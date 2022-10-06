package com.example.yolov5tfliteandroid.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.databinding.FragmentSettingBinding;
import com.example.yolov5tfliteandroid.ui.login.LoginActivity;

public class SettingFragment extends Fragment implements View.OnClickListener{
    private Button btn_exit;
    private TextView tv_name;
    private String name;
    private FragmentSettingBinding binding;
    public SettingFragment() {
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_setting,container,false);
        Intent intent = getActivity().getIntent();
        name=intent.getStringExtra("name");
        Log.d("TAG",name);
        //绑定组件
        btn_exit=view.findViewById(R.id.exitlogin);
        tv_name=view.findViewById(R.id.yonghuming);
        tv_name.setText(name);
        InitEvent();
        return view;
    }
    //初始化事件
    private void InitEvent(){
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.exitlogin:
                exit_login();
                break;

        }
    }

    //退出登录
    private void exit_login(){
        Intent intent=new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        //finish();
    }
}