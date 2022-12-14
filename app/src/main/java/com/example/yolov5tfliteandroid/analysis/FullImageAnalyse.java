package com.example.yolov5tfliteandroid.analysis;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Base64;
import android.util.FloatProperty;
import android.util.Log;
import android.util.Size;
import android.util.Xml;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.view.PreviewView;

import com.example.yolov5tfliteandroid.MainActivity;
import com.example.yolov5tfliteandroid.detector.Yolov5TFLiteDetector;
import com.example.yolov5tfliteandroid.repository.FileIO;
import com.example.yolov5tfliteandroid.utils.ImageProcess;
import com.example.yolov5tfliteandroid.utils.Recognition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlinx.coroutines.GlobalScope;

public class FullImageAnalyse implements ImageAnalysis.Analyzer {

    public static class Result{

        public Result(long costTime, Bitmap bitmap) {
            this.costTime = costTime;
            this.bitmap = bitmap;
        }
        long costTime;
        Bitmap bitmap;
    }
    Context context;
    ImageView boxLabelCanvas;
    PreviewView previewView;
    int rotation;
    private TextView inferenceTimeTextView;
    private TextView frameSizeTextView;
    ImageProcess imageProcess;
    private Yolov5TFLiteDetector yolov5TFLiteDetector;
    Integer number;//??????????????????????????????Int????????????
    public FullImageAnalyse(Context context,
                            PreviewView previewView,
                            ImageView boxLabelCanvas,
                            int rotation,
                            TextView inferenceTimeTextView,
                            TextView frameSizeTextView,
                            Yolov5TFLiteDetector yolov5TFLiteDetector) {
        this.context=context;
        this.previewView = previewView;
        this.boxLabelCanvas = boxLabelCanvas;
        this.rotation = rotation;
        this.inferenceTimeTextView = inferenceTimeTextView;
        this.frameSizeTextView = frameSizeTextView;
        this.imageProcess = new ImageProcess();
        this.yolov5TFLiteDetector = yolov5TFLiteDetector;
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        int previewHeight = previewView.getHeight();
        int previewWidth = previewView.getWidth();

        // ??????Observable???image analyse??????????????????????????????, ??????UI????????????????????????????????????, ????????????UI??????
        Observable.create( (ObservableEmitter<Result> emitter) -> {
                    long start = System.currentTimeMillis();

                    byte[][] yuvBytes = new byte[3][];
                    ImageProxy.PlaneProxy[] planes = image.getPlanes();
                    int imageHeight = image.getHeight();
                    int imagewWidth = image.getWidth();

                    imageProcess.fillBytes(planes, yuvBytes);
                    int yRowStride = planes[0].getRowStride();
                    final int uvRowStride = planes[1].getRowStride();
                    final int uvPixelStride = planes[1].getPixelStride();

                    int[] rgbBytes = new int[imageHeight * imagewWidth];
                    imageProcess.YUV420ToARGB8888(
                            yuvBytes[0],
                            yuvBytes[1],
                            yuvBytes[2],
                            imagewWidth,
                            imageHeight,
                            yRowStride,
                            uvRowStride,
                            uvPixelStride,
                            rgbBytes);

                    // ??????bitmap
                    Bitmap imageBitmap = Bitmap.createBitmap(imagewWidth, imageHeight, Bitmap.Config.ARGB_8888);
                    imageBitmap.setPixels(rgbBytes, 0, imagewWidth, 0, 0, imagewWidth, imageHeight);


                    // ??????????????????fill_start?????????bitmap
                    double scale = Math.max(
                            previewHeight / (double) (rotation % 180 == 0 ? imagewWidth : imageHeight),
                            previewWidth / (double) (rotation % 180 == 0 ? imageHeight : imagewWidth)
                    );
                    Matrix fullScreenTransform = imageProcess.getTransformationMatrix(
                            imagewWidth, imageHeight,
                            (int) (scale * imageHeight), (int) (scale * imagewWidth),
                            rotation % 180 == 0 ? 90 : 0, false
                    );

                    // ??????preview????????????bitmap
                    Bitmap fullImageBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imagewWidth, imageHeight, fullScreenTransform, false);
                    // ????????????preview???????????????????????????bitmap
                    Bitmap cropImageBitmap = Bitmap.createBitmap(fullImageBitmap, 0, 0, previewWidth, previewHeight);

                    // ???????????????bitmap
                    Matrix previewToModelTransform =
                            imageProcess.getTransformationMatrix(
                                    cropImageBitmap.getWidth(), cropImageBitmap.getHeight(),
                                    yolov5TFLiteDetector.getInputSize().getWidth(),
                                    yolov5TFLiteDetector.getInputSize().getHeight(),
                                    0, false);
                    Bitmap modelInputBitmap = Bitmap.createBitmap(cropImageBitmap, 0, 0,
                            cropImageBitmap.getWidth(), cropImageBitmap.getHeight(),
                            previewToModelTransform, false);

                    Matrix modelToPreviewTransform = new Matrix();
                    previewToModelTransform.invert(modelToPreviewTransform);

                    ArrayList<Recognition> recognitions = yolov5TFLiteDetector.detect(modelInputBitmap);//?????????????????????????????????
//            ArrayList<Recognition> recognitions = yolov5TFLiteDetector.detect(imageBitmap);

                    Bitmap emptyCropSizeBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888);//??????
                    Canvas cropCanvas = new Canvas(emptyCropSizeBitmap);
//            Paint white = new Paint();
//            white.setColor(Color.WHITE);
//            white.setStyle(Paint.Style.FILL);
//            cropCanvas.drawRect(new RectF(0,0,previewWidth, previewHeight), white);
                    // ????????????
                    Paint boxPaint = new Paint();
                    boxPaint.setStrokeWidth(5);
                    boxPaint.setStyle(Paint.Style.STROKE);
                    boxPaint.setColor(Color.RED);
                    // ????????????
                    Paint textPain = new Paint();
                    textPain.setTextSize(50);
                    textPain.setColor(Color.RED);
                    textPain.setStyle(Paint.Style.FILL);

                    SharedPreferences sharedPreferences = context.getSharedPreferences("Number", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();

                    //???????????????spre
                    SharedPreferences spreDate = context.getSharedPreferences("Date",Context.MODE_PRIVATE);
                    SharedPreferences.Editor dateEditor = spreDate.edit();

                    if (!recognitions.isEmpty()) {

                        number = sharedPreferences.getInt("number", 0);//0?????????????????????1????????????

                        FileIO.saveImage(number, imageBitmap);

                        //??????????????????
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        String dateStr=simpleDateFormat.format(date);

                        dateEditor.putString("date"+number,dateStr);
                        dateEditor.apply();



                        FileIO.saveRes(number, recognitions, modelToPreviewTransform, boxPaint, cropCanvas, textPain);
                        number++;
                        edit.putInt("number", number);
                        edit.apply();
                    }


                    long end = System.currentTimeMillis();
                    long costTime = (end - start);
                    image.close();
                    emitter.onNext(new Result(costTime, emptyCropSizeBitmap));
//            emitter.onNext(new Result(costTime, imageBitmap));

                }).subscribeOn(Schedulers.io()) // ????????????????????????,??????????????????????????????, ????????????????????????????????????, ?????????
                // ???????????????????????????, ??????????????????emitter???????????????????????????
                .observeOn(AndroidSchedulers.mainThread())
                // ?????????????????????????????????????????????????????????.
                .subscribe((Result result) -> {
                    boxLabelCanvas.setImageBitmap(result.bitmap);
                    frameSizeTextView.setText(previewHeight + "x" + previewWidth);
                    inferenceTimeTextView.setText(Long.toString(result.costTime) + "ms");
                });

    }
}
