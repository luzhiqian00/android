package com.example.yolov5tfliteandroid.ui.detect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yolov5tfliteandroid.databinding.FragmentDetectBinding;

public class DetectFragment extends Fragment {

    private FragmentDetectBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DetectViewModel detectViewModel =
                new ViewModelProvider(this).get(DetectViewModel.class);

        binding = FragmentDetectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDetect;
        detectViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}