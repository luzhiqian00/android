package com.example.yolov5tfliteandroid.ui.setting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yolov5tfliteandroid.Bottom;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.databinding.FragmentSettingBinding;
import com.example.yolov5tfliteandroid.ui.About.AboutActivity;
import com.example.yolov5tfliteandroid.ui.history.HistoryActivity;
import com.example.yolov5tfliteandroid.ui.login.LoginActivity;

public class SettingFragment extends Fragment implements View.OnClickListener{
    private Button btn_exit;
    private TextView tv_name;
    private String name;
    private Button history;
    private Button security;
    private Button about;
    //private FragmentSettingBinding binding;
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
        /*设置图标*/
        history=view.findViewById(R.id.but1);
        security=view.findViewById(R.id.but2);
        about=view.findViewById(R.id.but3);
        Drawable left1=getResources().getDrawable(R.drawable.left_history);
        left1.setBounds(0,0,80,80);//必须设置图片的大小否则没有作用
        history.setCompoundDrawables(left1,null ,null,null);//设置图片left这里如果是右边就放到第二个参数里面依次对应
        Drawable left2=getResources().getDrawable(R.drawable.left_security);
        left2.setBounds(0,0,80,80);//必须设置图片的大小否则没有作用
        security.setCompoundDrawables(left2,null ,null,null);//设置图片left这里如果是右边就放到第二个参数里面依次对应
        Drawable left3=getResources().getDrawable(R.drawable.left_about);
        left3.setBounds(0,0,80,80);//必须设置图片的大小否则没有作用
        about.setCompoundDrawables(left3,null ,null,null);//设置图片left这里如果是右边就放到第二个参数里面依次对应
        InitEvent();
        return view;
    }
    //初始化事件
    private void InitEvent(){
        btn_exit.setOnClickListener(this);
        history.setOnClickListener(this);
        about.setOnClickListener(this);
        security.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.exitlogin:
                exit_login();
                break;
            case R.id.but1:
                openhistory();
                break;
            case R.id.but2:
                break;
            case R.id.but3:
                break;
        }
    }
    //历史界面
    private void openhistory(){
        Intent intent=new Intent(getActivity(), HistoryActivity.class);
        startActivity(intent);
    }
    //退出登录
    private void exit_login(){
        Intent intent=new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        //finish();
    }
}