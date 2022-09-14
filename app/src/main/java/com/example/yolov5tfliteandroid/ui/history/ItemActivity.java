package com.example.yolov5tfliteandroid.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yolov5tfliteandroid.R;

public class ItemActivity extends AppCompatActivity {
    private int id;
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        id=intent.getIntExtra("position", 0);
        imageView=findViewById(R.id.image_item);
        textView=findViewById(R.id.text_item);
        textView.setText("Item:"+id);
    }
}
