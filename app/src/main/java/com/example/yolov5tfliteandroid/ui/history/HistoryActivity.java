package com.example.yolov5tfliteandroid.ui.history;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import com.example.yolov5tfliteandroid.MainActivity;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.YAApplication;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.YARepository;
//import com.example.yolov5tfliteandroid.databinding.FragmentHistoryBinding;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.ui.history.HistoryViewModel;
import com.example.yolov5tfliteandroid.repository.FileIO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener, RecyclerBuilder.OnRecyclerViewItemClick{
    private TextView editor_et; //右上角编辑按钮
    private TextView selectSum_tv; //显示当前选中的数量
    private Button deleteAll_btn; //删除按钮
    private Button selectAll_btn; //全部选中按钮
    private RecyclerView recyclerView; //列表
    private ConstraintLayout footBar; //底部的操作栏
    private TabLayout tabLayout; //切换栏

    private final List<ItemProperty> itemProperties = new ArrayList<>(); //item属性集合

    private RecyclerBuilder recyclerBuilder; //列表适配器

    private boolean isEditStatus = false; //是否是编辑状态
    private boolean isSelectAll = false; //是否是全选状态

    private int selectedSum; //已经选中的item的数量
    private int tabstate = 0;

    //    private FragmentHistoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);
        InitView();
        initData(); //初始化数据
        initEvent();
    }

    private void InitView(){
        editor_et = this.findViewById(R.id.editor);
        selectSum_tv = this.findViewById(R.id.selectSum);
        deleteAll_btn = this.findViewById(R.id.deleteAll);
        selectAll_btn = this.findViewById(R.id.selectAll);
        recyclerView = this.findViewById(R.id.recyclerView);
        footBar = this.findViewById(R.id.footBar);
        tabLayout = this.findViewById(R.id.tab_layout);
    }

    /**
     * 初始化数据，加载列表
     */
    private void initData() {
        Integer count = 0;
        recyclerBuilder = new RecyclerBuilder(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerBuilder); //设置适配器
        recyclerView.setLayoutManager(linearLayoutManager); //设置布局
//        添加数据
        //ItemProperty itemPropertyt = new ItemProperty();
        //itemPropertyt.setTime1("2022/10/26");
        //itemPropertyt.setTime2("16:59:59");
        //itemProperties.add(itemPropertyt);
        SharedPreferences spre = getSharedPreferences("Date",MODE_PRIVATE);
        String date;

        for (int i = 0; i < 1000; i++) {
            ItemProperty itemProperty = new ItemProperty();
            //itemProperty.setTime2("16:59:59");
            File file =null;
            ++count;
            //itemProperty.setTitle("第" + (++count) + "项");
            //TODO:接入数据库图片对应的存储时间
            //itemProperty.setTime1("2022/10/26");
            //itemProperty.setTime2("16:59:59");
            String fileName = YAApplication.fDir+i+".png";
            file = new File(fileName);
            itemProperty.setImageId(i);

            while(!file.exists() & i<1000){
                i++;
                fileName = YAApplication.fDir+i+".png";
                file = new File(fileName);
                itemProperty.setImageId(i);
            }
            if(file.exists()){
                date = spre.getString("date"+i,"");
                itemProperty.setTime1(date);
                itemProperty.setImageId(i);
                itemProperty.setImagePath(fileName);
                itemProperties.add(itemProperty);
            }
            recyclerBuilder.notifyList(itemProperties); //逐次刷新列表数据
        }
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }



    /**
     * 监听事件
     */
    private void initEvent() {
        recyclerBuilder.setOnRecyclerViewItemClick(this); //item的click
        editor_et.setOnClickListener(this); //编辑按钮
        selectAll_btn.setOnClickListener(this);
        deleteAll_btn.setOnClickListener(this);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(HistoryActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
                /*
                TODO 这里数据库再加一列判断完成状态然后查数据放进去
                 */
                if(tabstate == 1){
                    clearRecycleBuilder();//清空原有数据
                    //查找未完成数据
                    tabstate = 0;
                }else{
                    clearRecycleBuilder();//清空原有数据
                    //查找已经完成数据
                    tabstate = 1;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //replaceFragment(new f1());
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    //   @Override
    public void OnItemClick(int position, List<ItemProperty> itemProperties) {
//        位于编辑状态下
        if (isEditStatus){
            ItemProperty itemProperty = itemProperties.get(position);
//            如果item是选中状态
            if (itemProperty.isSelect()){
                selectedSum--;
                itemProperty.setSelect(false);
            }
//            非选中状态
            else {
                selectedSum++;
                itemProperty.setSelect(true);
            }

//            判断所有item是否都已经被选中
            if (selectedSum == itemProperties.size()){
                selectAll_btn.setText("取消全选");
                isSelectAll = true;
            }
            else {
                selectAll_btn.setText("全选");
                isSelectAll = false;
            }
//            设置选中个数显示，刷新列表
            selectSum_tv.setText("当前共选中: " + selectedSum + "个");
            recyclerBuilder.notifyDataSetChanged();
        }
//        没有位于编辑状态
        else {
            ItemProperty itemProperty = itemProperties.get(position);
            Toast.makeText(this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ItemActivity.class);
            intent.putExtra( "position", itemProperty.getImageId());
            startActivity(intent );// 启动Intent
        }
    }

    @SuppressLint("NonConstantResourceId")
    //  @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editor:
                editorClick();
                break;
            case R.id.selectAll:
                selectAllClick();
                break;
            case R.id.deleteAll:
                deleteAllClick();
        }
    }

    /**
     * 点击编辑按钮时，根据表示当前编辑状态的变量，判断进入或退出编辑模式
     */
    private void editorClick() {
        if (isEditStatus){
            editor_et.setText("编辑");
            footBar.setVisibility(View.GONE); //隐藏底部操作栏
            isEditStatus = false;
        }
        else {
            editor_et.setText("取消");
            footBar.setVisibility(View.VISIBLE); //显示底部操作栏
            isEditStatus = true;
            clearAllSelected(); //设置所有item为未选中
        }
//        设置编辑模式
        recyclerBuilder.setEditMode(isEditStatus);
    }

    /**
     * 点击全选按钮时，根据当前选中状态，调用全部选中或者取消全部选中
     */
    @SuppressLint("NotifyDataSetChanged")
    private void selectAllClick() {
//        是否已经在全选状态
        if (isSelectAll){
            clearAllSelected();
        }
        else {
            checkAllItem();
        }
//        刷新列表
        recyclerBuilder.notifyDataSetChanged();
    }

    /**
     * 全部选中
     */
    @SuppressLint("SetTextI18n")
    private void checkAllItem() {
//        从适配器中获取item属性的集合
        List<ItemProperty> itemProperties = recyclerBuilder.getItemProperties();
        selectedSum = itemProperties.size(); //选中个数
        selectSum_tv.setText("当前选中了: " + selectedSum + "个"); //设置选中个数显示
        isSelectAll = true; //进入全选状态
        selectAll_btn.setText("取消全选"); //设置按钮显示
//        遍历设置所有item为未选中状态
        for (int i = 0; i < itemProperties.size(); i++) {
            itemProperties.get(i).setSelect(true);
        }
    }

    /**
     * 取消所有选中
     */
    @SuppressLint("SetTextI18n")
    private void clearAllSelected() {
//        从适配器中获取item属性的集合
        List<ItemProperty> itemProperties = recyclerBuilder.getItemProperties();
        selectedSum = 0; //选中个数
        selectSum_tv.setText("当前选中了: " + selectedSum + "个"); //设置选中个数显示
        isSelectAll = false; //退出全选状态
        selectAll_btn.setText("全选"); //设置按钮显示
        for (int i = 0; i < itemProperties.size(); i++) {
            itemProperties.get(i).setSelect(false);
        }
    }

    /**
     * 删除按钮
     */
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void deleteAllClick() {
        if (selectedSum == 0){
            Toast.makeText(this, "没有选中", Toast.LENGTH_SHORT).show();
            return;
        }

//        dialog提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确定删除吗")
                .setMessage("将会删除" + selectedSum + "个item")
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    deleteSelected(); //删除选中
                    dialogInterface.dismiss();
                })
                .setNeutralButton("取消", (dialogInterface, i) -> dialogInterface.dismiss())
                .create().show();
    }

    /**
     * 删除选中
     */
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void deleteSelected() {
//        从适配器中获取item属性的集合
        List<ItemProperty> itemProperties = recyclerBuilder.getItemProperties();
//        循环判断选中状态的item属性类，并从item属性集合中删除
        for (int i = itemProperties.size()-1; i >= 0; i--){
            ItemProperty itemProperty = itemProperties.get(i);
            if (itemProperty.isSelect()){
                itemProperties.remove(itemProperty);

                File file = new File(itemProperty.getImagePath());//删除图片
                deleteFile(file.getName());

                FileIO.deleteImage(itemProperty.getImageName());//删除数据库里的数据
                selectedSum--;
            }
        }
        selectSum_tv.setText("当前选中了: " + selectedSum + "个"); //设置选中个数显示
        recyclerBuilder.notifyList(itemProperties); //刷新数据

    }

    private void clearRecycleBuilder(){
        List<ItemProperty> itemProperties = recyclerBuilder.getItemProperties();
//        循环判断选中状态的item属性类，并从item属性集合中删除
        for (int i = itemProperties.size()-1; i >= 0; i--){
            ItemProperty itemProperty = itemProperties.get(i);
            itemProperties.remove(itemProperty);
        }
        recyclerBuilder.notifyList(itemProperties); //刷新数据
    }

}