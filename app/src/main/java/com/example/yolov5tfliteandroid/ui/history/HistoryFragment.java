package com.example.yolov5tfliteandroid.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import java.util.ArrayList;
import java.util.List;

import com.example.yolov5tfliteandroid.MainActivity;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.databinding.FragmentHistoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

public class HistoryFragment extends Fragment implements View.OnClickListener, RecyclerBuilder.OnRecyclerViewItemClick{
    private TextView editor_et; //?????????????????????
    private TextView selectSum_tv; //???????????????????????????
    private Button deleteAll_btn; //????????????
    private Button selectAll_btn; //??????????????????
    private RecyclerView recyclerView; //??????
    private ConstraintLayout footBar; //??????????????????

    private final List<ItemProperty> itemProperties = new ArrayList<>(); //item????????????

    private RecyclerBuilder recyclerBuilder; //???????????????

    private boolean isEditStatus = false; //?????????????????????
    private boolean isSelectAll = false; //?????????????????????

    private int selectedSum; //???????????????item?????????
    private FragmentHistoryBinding binding;
    public HistoryFragment() {
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history, container, false);

        //View view=View.inflate(getActivity(),R.layout.fragment_history,container);
        editor_et = view.findViewById(R.id.editor);
        selectSum_tv = view.findViewById(R.id.selectSum);
        deleteAll_btn = view.findViewById(R.id.deleteAll);
        selectAll_btn = view.findViewById(R.id.selectAll);
        recyclerView = view.findViewById(R.id.recyclerView);
        footBar = view.findViewById(R.id.footBar);
        initData(); //???????????????
        initEvent(); //???????????????
        return view;
    }
///????????????????????????

    /**
     * ??????????????????????????????
     */
    private void initData() {
        recyclerBuilder = new RecyclerBuilder(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(recyclerBuilder); //???????????????
        recyclerView.setLayoutManager(linearLayoutManager); //????????????
//        ????????????
        for (int i = 0; i < 100; i++) {
            ItemProperty itemProperty = new ItemProperty();
            //itemProperty.setTitle("???" + i + "???");
            itemProperties.add(itemProperty);
            recyclerBuilder.notifyList(itemProperties); //????????????????????????
        }
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    /**
     * ????????????
     */
    private void initEvent() {
        recyclerBuilder.setOnRecyclerViewItemClick(this); //item???click
        editor_et.setOnClickListener(this); //????????????
        selectAll_btn.setOnClickListener(this);
        deleteAll_btn.setOnClickListener(this);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    //   @Override
    public void OnItemClick(int position, List<ItemProperty> itemProperties) {
//        ?????????????????????
        if (isEditStatus){
            ItemProperty itemProperty = itemProperties.get(position);
//            ??????item???????????????
            if (itemProperty.isSelect()){
                selectedSum--;
                itemProperty.setSelect(false);
            }
//            ???????????????
            else {
                selectedSum++;
                itemProperty.setSelect(true);
            }

//            ????????????item????????????????????????
            if (selectedSum == itemProperties.size()){
                selectAll_btn.setText("????????????");
                isSelectAll = true;
            }
            else {
                selectAll_btn.setText("??????");
                isSelectAll = false;
            }
//            ???????????????????????????????????????
            selectSum_tv.setText("???????????????: " + selectedSum + "???");
            recyclerBuilder.notifyDataSetChanged();
        }
//        ????????????????????????
        else {
            Toast.makeText(getActivity(), "????????????" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), ItemActivity.class);
            intent.putExtra( "position", position );
            startActivity(intent );// ??????Intent
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
     * ???????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private void editorClick() {
        if (isEditStatus){
            editor_et.setText("??????");
            footBar.setVisibility(View.GONE); //?????????????????????
            isEditStatus = false;
        }
        else {
            editor_et.setText("??????");
            footBar.setVisibility(View.VISIBLE); //?????????????????????
            isEditStatus = true;
            clearAllSelected(); //????????????item????????????
        }
//        ??????????????????
        recyclerBuilder.setEditMode(isEditStatus);
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????
     */
    @SuppressLint("NotifyDataSetChanged")
    private void selectAllClick() {
//        ???????????????????????????
        if (isSelectAll){
            clearAllSelected();
        }
        else {
            checkAllItem();
        }
//        ????????????
        recyclerBuilder.notifyDataSetChanged();
    }

    /**
     * ????????????
     */
    @SuppressLint("SetTextI18n")
    private void checkAllItem() {
//        ?????????????????????item???????????????
        List<ItemProperty> itemProperties = recyclerBuilder.getItemProperties();
        selectedSum = itemProperties.size(); //????????????
        selectSum_tv.setText("???????????????: " + selectedSum + "???"); //????????????????????????
        isSelectAll = true; //??????????????????
        selectAll_btn.setText("????????????"); //??????????????????
//        ??????????????????item??????????????????
        for (int i = 0; i < itemProperties.size(); i++) {
            itemProperties.get(i).setSelect(true);
        }
    }

    /**
     * ??????????????????
     */
    @SuppressLint("SetTextI18n")
    private void clearAllSelected() {
//        ?????????????????????item???????????????
        List<ItemProperty> itemProperties = recyclerBuilder.getItemProperties();
        selectedSum = 0; //????????????
        selectSum_tv.setText("???????????????: " + selectedSum + "???"); //????????????????????????
        isSelectAll = false; //??????????????????
        selectAll_btn.setText("??????"); //??????????????????
        for (int i = 0; i < itemProperties.size(); i++) {
            itemProperties.get(i).setSelect(false);
        }
    }

    /**
     * ????????????
     */
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void deleteAllClick() {
        if (selectedSum == 0){
            Toast.makeText(getActivity(), "????????????", Toast.LENGTH_SHORT).show();
            return;
        }

//        dialog??????
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("???????????????")
                .setMessage("????????????" + selectedSum + "???item")
                .setPositiveButton("??????", (dialogInterface, i) -> {
                    deleteSelected(); //????????????
                    dialogInterface.dismiss();
                })
                .setNeutralButton("??????", (dialogInterface, i) -> dialogInterface.dismiss())
                .create().show();
    }

    /**
     * ????????????
     */
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void deleteSelected() {
//        ?????????????????????item???????????????
        List<ItemProperty> itemProperties = recyclerBuilder.getItemProperties();
//        ???????????????????????????item??????????????????item?????????????????????
        for (int i = itemProperties.size()-1; i >= 0; i--){
            ItemProperty itemProperty = itemProperties.get(i);
            if (itemProperty.isSelect()){
                itemProperties.remove(itemProperty);
                selectedSum--;
            }
        }
        selectSum_tv.setText("???????????????: " + selectedSum + "???"); //????????????????????????
        recyclerBuilder.notifyDataSetChanged(); //????????????
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}