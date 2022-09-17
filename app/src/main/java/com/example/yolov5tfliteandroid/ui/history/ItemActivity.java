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
import kotlinx.coroutines.GlobalScope;

import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.YAApplication;
import com.example.yolov5tfliteandroid.analysis.AppDataBase;
import com.example.yolov5tfliteandroid.analysis.ImageDataBase;
import com.example.yolov5tfliteandroid.analysis.ImageDataBaseDao;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view.EMImageView;
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
        EMImageView imageView=findViewById(R.id.historyImage);
        File file=new File(YAApplication.context.getFilesDir().toString() + "/image8"  + ".png");

        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath()).copy(Bitmap.Config.ARGB_8888, true);
//        Bitmap emptyCropSizeBitmap=Bitmap.createBitmap(1080,1440,Bitmap.Config.ARGB_8888);
        Canvas canvas =new Canvas(bitmap);
        imageView.draw(canvas);
        imageView.setImageBitmap(bitmap);
    }
}
