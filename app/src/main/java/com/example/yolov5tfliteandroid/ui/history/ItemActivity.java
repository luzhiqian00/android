package com.example.yolov5tfliteandroid.ui.history;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.YAApplication;
import com.example.yolov5tfliteandroid.analysis.ImageDataBase;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.ui.history.HistoryViewModel;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view.EMImageView;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view.YAImageView;

import org.w3c.dom.Text;

import java.io.File;
import java.nio.DoubleBuffer;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemActivity extends AppCompatActivity {
    private HistoryViewModel model;
    private Integer id;
    private YAImageView imageView;
    private TextView textView;
    private TextView locationInfo;//百度测试

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        model = new ViewModelProvider(this).get(HistoryViewModel.class);

        Intent intent = getIntent();
        id=intent.getIntExtra("position", 0);

        model.getImageDataBase(id);

        imageView=findViewById(R.id.image_item);
        textView=findViewById(R.id.text_item);
        textView.setText("Item:"+id);

        //百度测试
        locationInfo = findViewById(R.id.locationInfo);

        File file=new File(YAApplication.context.getFilesDir().toString() + "/image"+id.toString()+".png");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath()).copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas =new Canvas(bitmap);
        imageView.setImageBitmap(bitmap);


        model.getMImageDataBase().observe(
                this, imageDataBase -> {
                    imageView.draw(canvas,imageDataBase);
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(imageDataBase.getLatitude()).append("\n");
                    currentPosition.append("经度：").append(imageDataBase.getLongitude()).append("\n");
                    locationInfo.setText(currentPosition);
                    locationInfo.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Intent i1 = new Intent();
// 展示地图
                            i1.setData(Uri.parse("baidumap://map/marker?location="+imageDataBase.getLatitude()+","+imageDataBase.getLongitude()+"&title=Marker&content=makeamarker&traffic=on&src=andr.baidu.openAPIdemo"));

                            startActivity(i1);

                        }

                    });
                }
        );


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
