package com.example.yolov5tfliteandroid.ui.history;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.YAApplication;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view.EMImageView;

import org.w3c.dom.Text;

import java.io.File;
import java.nio.DoubleBuffer;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemActivity extends AppCompatActivity {
    private Integer id;
    private  EMImageView imageView;
    private TextView textView;
    private TextView locationInfo;//百度测试
    LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        id=intent.getIntExtra("position", 0);

        imageView=findViewById(R.id.image_item);
        textView=findViewById(R.id.text_item);
        textView.setText("Item:"+id);

        //百度测试
        locationInfo = findViewById(R.id.locationInfo);
//        LocationClient.setAgreePrivacy(true);
//
//        try {
//            mLocationClient = new LocationClient(getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        mLocationClient.registerLocationListener(myListener);
//
//
//
//        List<String> permissionList = new ArrayList<String>();
//
//        if(ContextCompat.checkSelfPermission(ItemActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if(ContextCompat.checkSelfPermission(ItemActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if(ContextCompat.checkSelfPermission(ItemActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if(!permissionList.isEmpty()){
//            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
//            ActivityCompat.requestPermissions(ItemActivity.this,permissions,1);
//        }else{
//            requestLocation();
//        }


        File file=new File(YAApplication.context.getFilesDir().toString() + "/image"+id.toString()+".png");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath()).copy(Bitmap.Config.ARGB_8888, true);

        Double[] array = new Double[2];
        StringBuilder currentPosition = new StringBuilder();
        Canvas canvas =new Canvas(bitmap);
        array=imageView.draw(canvas,id);
        imageView.setImageBitmap(bitmap);
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentPosition.append("纬度：").append(array[0].toString()).append("\n");
        currentPosition.append("经度：").append(array[1].toString()).append("\n");
        locationInfo.setText(currentPosition);
    }

//    private class MyLocationListener extends BDAbstractLocationListener{
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            StringBuilder currentPosition = new StringBuilder();
//            currentPosition.append("状态码：").append(location.getLocType()).append("\n");
//            currentPosition.append("维度：").append(location.getLatitude()).append("\n");
//            currentPosition.append("经度：").append(location.getLongitude()).append("\n");
//            currentPosition.append("国家：").append(location.getCountry()).append("\n");
//            currentPosition.append("省：").append(location.getProvince()).append("\n");
//            currentPosition.append("市：").append(location.getCity()).append("\n");
//            currentPosition.append("区：").append(location.getDistrict()).append("\n");
//            currentPosition.append("村镇：").append(location.getTown()).append("\n");
//            currentPosition.append("街道：").append(location.getStreet()).append("\n");
//            currentPosition.append("地址：").append(location.getAddrStr()).append("\n");
//            currentPosition.append("定位方式：");
//
//            if(location.getLocType() == BDLocation.TypeGpsLocation){
//                currentPosition.append("GPS");
//
//        mLocationClient.stop();
//            }else if(location.getLocType() == BDLocation.TypeNetWorkLocation){
//                currentPosition.append("网络");
//
//        mLocationClient.stop();
//            }
//
//            locationInfo.setText(currentPosition);
//        }
//    }
//
//    private void requestLocation(){
//        initLocation();
//        mLocationClient.start();
////        mLocationClient.stop();
//    }
//
//
//    private void initLocation(){
//        LocationClientOption option = new LocationClientOption();
//
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        //可选，设置定位模式，默认高精度
//        //LocationMode.Hight_Accuracy：高精度；
//        //LocationMode. Battery_Saving：低功耗；
//        //LocationMode. Device_Sensors：仅使用设备；
//        //LocationMode.Fuzzy_Locating, 模糊定位模式；v9.2.8版本开始支持，可以降低API的调用频率，但同时也会降低定位精度；
//
//        option.setCoorType("bd09ll");
//        //可选，设置返回经纬度坐标类型，默认GCJ02
//        //GCJ02：国测局坐标；
//        //BD09ll：百度经纬度坐标；
//        //BD09：百度墨卡托坐标；
//        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
//
//        option.setScanSpan(1000);
//
//        option.setOpenGps(true);
//
//        option.setLocationNotify(true);
//
//        option.setIgnoreKillProcess(false);
//
//        option.SetIgnoreCacheException(false);
//
//        option.setWifiCacheTimeOut(5*60*1000);
//
//        option.setEnableSimulateGps(false);
//
//        option.setIsNeedAddress(true);
//
//        mLocationClient.setLocOption(option);
//    }

}
