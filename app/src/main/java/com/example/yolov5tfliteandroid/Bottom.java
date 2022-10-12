package com.example.yolov5tfliteandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.yolov5tfliteandroid.adapter.MyFragmentStateAdapter;
import com.example.yolov5tfliteandroid.utils.UIUtils;
import com.example.yolov5tfliteandroid.viewmodel.MainViewModel;

import java.util.Arrays;
import java.util.List;


public class Bottom extends AppCompatActivity {

    private ViewPager2 viewPager2;
    public RadioGroup radioGroup;
    private List<Integer> radioButtonIdList = Arrays.asList(R.id.rb_detect, R.id.rb_setting);
    //private List<Integer> radioButtonIdList = Arrays.asList(R.id.rb_detect, R.id.rb_history, R.id.rb_setting);
    private String name;//用户名
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        initView();
        /*
        从ViewModel中获取FragmentList数据
        获取ViewModel对象的方式固定为：ViewModelProviders.of(当前activity).get(ViewModel类名.class);
        */
        final MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        List<Fragment> fragmentList = mainViewModel.getFragmentList();
        viewPager2 = findViewById(R.id.activity_main_viewPager2);
        // 将FragmentList传递给FragmentStateAdapter
        MyFragmentStateAdapter myFragmentStateAdapter = new MyFragmentStateAdapter(this, fragmentList);
        viewPager2.setAdapter(myFragmentStateAdapter);

        /*
        页面切换的监听事件
         */
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                radioGroup.check(radioButtonIdList.get(position));
            }
        });
    }

    private void initView() {
        // 初始化UI控件
        radioGroup = findViewById(R.id.activity_main_radiogroup);
        RadioButton rbdetect = findViewById(R.id.rb_detect);
        //RadioButton rbhistory = findViewById(R.id.rb_history);
        RadioButton rbsetting = findViewById(R.id.rb_setting);
        // RadioButton点击响应事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewPager2.setCurrentItem(radioButtonIdList.indexOf(checkedId));
            }
        });

        // 四个RadioButton初始化图片、文字
        Drawable dbHome = getResources().getDrawable(R.drawable.selector_home);
        dbHome.setBounds(0, 0, UIUtils.dipTopx(this, 25), UIUtils.dipTopx(this, 25));
        rbdetect.setCompoundDrawables(null, dbHome, null, null);

        //Drawable dbPond = getResources().getDrawable(R.drawable.selector_pond);
        //dbPond.setBounds(0, 0, UIUtils.dipTopx(this, 25), UIUtils.dipTopx(this, 25));
        //rbhistory.setCompoundDrawables(null, dbPond, null, null);

        Drawable dbMsg = getResources().getDrawable(R.drawable.selector_person);
        dbMsg.setBounds(0, 0, UIUtils.dipTopx(this, 25), UIUtils.dipTopx(this, 25));
        rbsetting.setCompoundDrawables(null, dbMsg, null, null);
    }

}
