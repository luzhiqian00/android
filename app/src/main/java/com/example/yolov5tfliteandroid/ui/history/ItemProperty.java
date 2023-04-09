package com.example.yolov5tfliteandroid.ui.history;


import com.example.yolov5tfliteandroid.analysis.ImageDataBase;

public class ItemProperty {
    private String time1; //item标题
    private String location;
    //private String time2;
    private boolean isSelect; //是否选中
    private String imagePath;   // 图片本地地址
    private long imageId;   //图片的id，也是存储的数据库中的id
    private int finishState;
    private ImageDataBase imageDataBase;

    public ImageDataBase getImageDataBase(){return imageDataBase;}
    public void setImageDataBase(ImageDataBase a){this.imageDataBase = a;}

    public  long getImageId() {return imageId;}

    public void setImageId(long imageId) {this.imageId = imageId;}

    public String getTime1() {
        return time1;
    }

    public String getLocation(){return location;}
    public boolean isSelect() {
        return isSelect;
    }

    public void setTime1(String time) {
        this.time1 = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    //public void setTime2(String time){this.time2=time;}

    public void setSelect(boolean select) {
        isSelect = select;
    }

    //public String getTime2(){return time2;}

    public String getImagePath() {
        return imagePath;
    }

    public String getImageName(){
        return imagePath.split("/")[6];
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setFinishState(int finishState){this.finishState=finishState;}
    public int getFinishState(){return finishState;}


}
