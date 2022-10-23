package com.example.yolov5tfliteandroid.ui.history;

public class ItemProperty {
    private String title; //item标题
    private boolean isSelect; //是否选中
    private String imagePath;   // 图片本地地址
    public String getTitle() {
        return title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
