package com.example.yolov5tfliteandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import com.example.yolov5tfliteandroid.ui.detect.DetectFragment;
import com.example.yolov5tfliteandroid.ui.history.HistoryFragment;
import com.example.yolov5tfliteandroid.ui.setting.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Fragment> getFragmentList(){
        /* 展示三个页面 */
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DetectFragment());
        //fragmentList.add(new HistoryFragment());
        fragmentList.add(new SettingFragment());
        return fragmentList;
    }
}
