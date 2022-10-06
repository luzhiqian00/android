package com.example.yolov5tfliteandroid.adapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyFragmentStateAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;
    public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }
    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
