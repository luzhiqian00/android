package com.example.yolov5tfliteandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Bottom extends AppCompatActivity {

    private BottomNavigationView btmMenuView;
    private TextView btmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        btmMenuView = findViewById(R.id.btmMenu);
        btmTextView = findViewById(R.id.text_msg);

        btmMenuView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.detect:
                    btmTextView.setText(R.string.detect);
                    return true;
                case R.id.history:
                    btmTextView.setText(R.string.history);
                    return true;
                case R.id.setting:
                    btmTextView.setText(R.string.setting);
                    return true;
            }
            return false;
        });
    }
}