package com.example.yolov5tfliteandroid.ui.history;

public class ItemProperty {
    private String time1; //item标题
    private String time2;
    private boolean isSelect; //是否选中
    private String imagePath;   // 图片本地地址
    private int imageId;   //图片的id，也是存储的数据库中的id

    public  int getImageId() {return imageId;}

    public void setImageId(int imageId) {this.imageId = imageId;}

    public String getTime1() {
        return time1;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setTime1(String time) {
        this.time1 = time;
    }

    public void setTime2(String time){this.time2=time;}

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTime2(){return time2;}

    public String getImagePath() {
        return imagePath;
    }

    public String getImageName(){
        return imagePath.split("/")[6];
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
