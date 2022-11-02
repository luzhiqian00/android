package com.example.yolov5tfliteandroid.ui.About;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yolov5tfliteandroid.R;

public class AboutActivity extends AppCompatActivity {
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        InitView();

    }
    private void InitView(){
        content=this.findViewById(R.id.TitleText1);
        content.setText("一款检测路面病害的软件");
    }
}
