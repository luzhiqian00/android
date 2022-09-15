package com.example.yolov5tfliteandroid.ui.history;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.impl.ImageInputConfig;

import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.YAApplication;
import com.example.yolov5tfliteandroid.analysis.ImageDataBase;
import com.example.yolov5tfliteandroid.analysis.ImageDataBaseDao;
import com.example.yolov5tfliteandroid.utils.ImageProcess;
import com.example.yolov5tfliteandroid.utils.Recognition;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ImageView imageView=findViewById(R.id.historyImage);
        File file=new File(YAApplication.context.getFilesDir().toString() + "/image1"  + ".png");

        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        Canvas canvas =new Canvas(bitmap);
        Paint paintRect = new Paint();
        paintRect.setColor(Color.rgb(79, 129, 189));
        paintRect.setStrokeWidth(2);
        paintRect.setStyle(Paint.Style.STROKE);

        canvas.drawRect(20,20,100,100,paintRect);

        imageView.setImageBitmap(bitmap);


       /* ImageDataBaseDao imageDataBaseDao=null;
       List<ImageDataBase> imageList=imageDataBaseDao.loadImageDabaBaseImageNumber("image1");


        int previewWidth=1080;
        int previewHeight=1440;
*/

       /* Bitmap emptyCropSizeBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888);//空图
        Canvas cropCanvas = new Canvas(emptyCropSizeBitmap);

        // 边框画笔
        Paint boxPaint = new Paint();
        boxPaint.setStrokeWidth(5);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setColor(Color.RED);
        // 字体画笔
        Paint textPain = new Paint();
        textPain.setTextSize(50);
        textPain.setColor(Color.RED);
        textPain.setStyle(Paint.Style.FILL);
*/
        /*ImageDataBase res;
        for (int i=0;i<imageList.size();i++)
        {
            res=imageList.get(i);
            RectF location=null;
            location.top=res.getTop();
            location.left=res.getLeft();
            location.right=res.getRight();
            location.bottom=res.getBottom();
            String label = res.getLabel();
            Float confidence = res.getConfidence();
//            modelToPreviewTransform.mapRect(location);
            cropCanvas.drawRect(location, boxPaint);
            cropCanvas.drawText(
                    label + ":" + String.format("%.2f", confidence),
                    location.left,
                    location.top,
                    textPain
            );
        }*/
    }
}
