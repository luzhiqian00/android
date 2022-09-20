package com.example.yolov5tfliteandroid.ui.history;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.YAApplication;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view.EMImageView;

import java.io.File;

public class ItemActivity extends AppCompatActivity {
    private Integer id;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        id=intent.getIntExtra("position", 0);
        //
        EMImageView  imageView=findViewById(R.id.image_item);
        textView=findViewById(R.id.text_item);
        textView.setText("Item:"+id);
        File file=new File(YAApplication.context.getFilesDir().toString() + "/image" +id.toString() + ".png");

        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath()).copy(Bitmap.Config.ARGB_8888, true);
//        Bitmap emptyCropSizeBitmap=Bitmap.createBitmap(1080,1440,Bitmap.Config.ARGB_8888);
        Canvas canvas =new Canvas(bitmap);
        imageView.draw(canvas,id);
        imageView.setImageBitmap(bitmap);

    }
}
