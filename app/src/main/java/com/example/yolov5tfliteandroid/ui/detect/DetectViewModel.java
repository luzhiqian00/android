package com.example.yolov5tfliteandroid.ui.detect;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetectViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DetectViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is detect fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}