package com.example.yolov5tfliteandroid.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    public SettingFragment() {
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_setting,container,false);
        return view;
    }

}