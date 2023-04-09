package com.example.yolov5tfliteandroid.ui.history;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
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
import com.example.yolov5tfliteandroid.analysis.AppDataBase;
import com.example.yolov5tfliteandroid.analysis.ImageDataBase;
import com.example.yolov5tfliteandroid.analysis.ImageDataBaseDao;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.ui.history.HistoryViewModel;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view.EMImageView;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view.YAImageView;
import com.example.yolov5tfliteandroid.repository.FileIO;

import org.w3c.dom.Text;

import java.io.File;
import java.nio.DoubleBuffer;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ItemActivity extends AppCompatActivity {
    private HistoryViewModel model;
    private YAImageView imageView;
    private String filepath;
    private String time;
    private Long id;
    private String location;
    private ImageDataBase item;
    private TextView textView;
    private TextView locationInfo;//百度测试
    private Button navigate;
    private Button label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        model = new ViewModelProvider(this).get(HistoryViewModel.class);

        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);
        filepath=intent.getStringExtra("filepath");
        time = intent.getStringExtra("time");
        location = intent.getStringExtra("location");
        item = (ImageDataBase) intent.getParcelableExtra("item");



        String q1[]=filepath.split("image");
        String q2[]=q1[1].split("\\.");
        int iid=Integer.parseInt(q2[0]);
        model.getImageDataBase(iid);

        navigate = findViewById(R.id.navigate);
        label = findViewById(R.id.label);
        imageView=findViewById(R.id.image_item);
        textView=findViewById(R.id.time);
        textView.setText(time);

        if(item.getFinish_status()=='1'){
            label.setText("已完成");
            label.setBackgroundColor(Color.RED);
        }
        item.setId(id);
        //百度测试
        locationInfo = findViewById(R.id.location);
        locationInfo.setText(location);

        File file=new File(filepath);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath()).copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas =new Canvas(bitmap);
        imageView.setImageBitmap(bitmap);


        model.getMImageDataBase().observe(
                this, imageDataBase -> {
                    imageView.draw(canvas,imageDataBase);
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(imageDataBase.getLatitude()).append("\n");
                    currentPosition.append("经度：").append(imageDataBase.getLongitude()).append("\n");
                    //locationInfo.setText(currentPosition);
                    navigate.setOnClickListener(new View.OnClickListener(){
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
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageDataBase newItem = new ImageDataBase(item.getImageName(), item.getLabel(),item.getConfidence(),
//                        item.getLeft(),item.getTop(),item.getRight(),item.getBottom(),1,item.getCreateTime(),
//                        item.getLatitude(),item.getLongitude(),item.getLocation());
                if(item.getFinish_status()==0){
                    item.setFinish_status(1);
                    FileIO.updateImage(item);
                    Toast.makeText(getApplicationContext(), "标记成功！ ", Toast.LENGTH_LONG).show();
                    label.setText("已完成");
                    label.setBackgroundColor(Color.RED);
                }else{
                    Toast.makeText(getApplicationContext(), "已完成！ ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}