package com.example.yolov5tfliteandroid.ui.detect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.yolov5tfliteandroid.MainActivity;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.databinding.FragmentSettingBinding;

public class DetectFragment extends Fragment {
    //private FragmentSettingBinding binding;
    private Button bt_detect;
    public DetectFragment() {
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        View view = inflater.inflate(R.layout.fragment_detect, container,false);
        bt_detect=view.findViewById(R.id.bt_detect);
        bt_detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}

/*
    <Button
        android:id="@+id/bt_detect2"
        android:layout_width="220dp"
        android:layout_height="200dp"
        android:layout_marginTop="200dp"
        android:background="@drawable/photo"
         />
 */


