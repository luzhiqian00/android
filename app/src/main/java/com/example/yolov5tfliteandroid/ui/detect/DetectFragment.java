package com.example.yolov5tfliteandroid.ui.detect;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yolov5tfliteandroid.MainActivity;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.analysis.FullImageAnalyse;
import com.example.yolov5tfliteandroid.analysis.FullScreenAnalyse;
import com.example.yolov5tfliteandroid.databinding.FragmentDetectBinding;
import com.example.yolov5tfliteandroid.detector.Yolov5TFLiteDetector;
import com.example.yolov5tfliteandroid.utils.CameraProcess;
import com.google.common.util.concurrent.ListenableFuture;

public class DetectFragment extends Fragment {

    private FragmentDetectBinding binding;
    private boolean IS_FULL_SCREEN = false;   //是否是全屏

    private PreviewView cameraPreviewMatch;
    private PreviewView cameraPreviewWrap;
    private ImageView boxLabelCanvas;
    private Spinner modelSpinner;
    private Switch immersive;//开关
    private TextView inferenceTimeTextView;
    private TextView frameSizeTextView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Yolov5TFLiteDetector yolov5TFLiteDetector;

    private CameraProcess cameraProcess = new CameraProcess();

    /**
     * 获取屏幕旋转角度,0表示拍照出来的图片是横屏
     *
     */
    protected int getScreenOrientation() {
        switch (getActivity().getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_270:
                return 270;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_90:
                return 90;
            default:
                return 0;
        }
    }

    /**
     * 加载模型
     *
     * @param modelName
     */
    private void initModel(String modelName) {
        // 加载模型
        try {
            this.yolov5TFLiteDetector = new Yolov5TFLiteDetector();
            this.yolov5TFLiteDetector.setModelFile(modelName);
//            this.yolov5TFLiteDetector.addNNApiDelegate();
            this.yolov5TFLiteDetector.addGPUDelegate();
            this.yolov5TFLiteDetector.initialModel(getActivity());
            Log.i("model", "Success loading model" + this.yolov5TFLiteDetector.getModelFile());
        } catch (Exception e) {
            Log.e("image", "load model error: " + e.getMessage() + e.toString());
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        View layout = inflater.inflate(R.layout.fragment_detect, null);

        // 打开app的时候隐藏顶部状态栏
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);

        // 全屏画面
        cameraPreviewMatch = layout.findViewById(R.id.camera_preview_match);
        cameraPreviewMatch.setScaleType(PreviewView.ScaleType.FILL_START);

        // 全图画面
        cameraPreviewWrap = layout.findViewById(R.id.camera_preview_wrap);
//        cameraPreviewWrap.setScaleType(PreviewView.ScaleType.FILL_START);

        // box/label画面
        boxLabelCanvas = layout.findViewById(R.id.box_label_canvas);

        // 下拉按钮
        modelSpinner = layout.findViewById(R.id.model);

        // 沉浸式体验按钮
        immersive = layout.findViewById(R.id.immersive);

        // 实时更新的一些view
        inferenceTimeTextView = layout.findViewById(R.id.inference_time);
        frameSizeTextView = layout.findViewById(R.id.frame_size);
        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());

        // 申请摄像头权限
        if (!cameraProcess.allPermissionsGranted(getActivity())) {
            cameraProcess.requestPermissions(getActivity());
        }

        // 获取手机摄像头拍照旋转参数
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        Log.i("image", "rotation: " + rotation);

        cameraProcess.showCameraSupportSize(getActivity());

        // 初始化加载yolov5s
        initModel("yolov5s");

        // 监听模型切换按钮
        modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String model = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(getActivity(), "loading model: " + model, Toast.LENGTH_LONG).show();
                initModel(model);
                if(IS_FULL_SCREEN){
                    cameraPreviewWrap.removeAllViews();
                    FullScreenAnalyse fullScreenAnalyse = new FullScreenAnalyse(getActivity(),
                            cameraPreviewMatch,
                            boxLabelCanvas,
                            rotation,
                            inferenceTimeTextView,
                            frameSizeTextView,
                            yolov5TFLiteDetector);
                    cameraProcess.startCamera(getActivity(), fullScreenAnalyse, cameraPreviewMatch);
                }else{
                    cameraPreviewMatch.removeAllViews();
                    FullImageAnalyse fullImageAnalyse = new FullImageAnalyse(
                            getActivity(),
                            cameraPreviewWrap,
                            boxLabelCanvas,
                            rotation,
                            inferenceTimeTextView,
                            frameSizeTextView,
                            yolov5TFLiteDetector);
                    cameraProcess.startCamera(getActivity(), fullImageAnalyse, cameraPreviewWrap);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 监听视图变化按钮
        immersive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                IS_FULL_SCREEN = b;
                if (b) {
                    // 进入全屏模式
                    cameraPreviewWrap.removeAllViews();
                    FullScreenAnalyse fullScreenAnalyse = new FullScreenAnalyse(getActivity(),
                            cameraPreviewMatch,
                            boxLabelCanvas,
                            rotation,
                            inferenceTimeTextView,
                            frameSizeTextView,
                            yolov5TFLiteDetector);
                    cameraProcess.startCamera(getActivity(), fullScreenAnalyse, cameraPreviewMatch);

                } else {
                    // 进入全图模式
                    cameraPreviewMatch.removeAllViews();
                    FullImageAnalyse fullImageAnalyse = new FullImageAnalyse(
                            getActivity(),
                            cameraPreviewWrap,
                            boxLabelCanvas,
                            rotation,
                            inferenceTimeTextView,
                            frameSizeTextView,
                            yolov5TFLiteDetector);
                    cameraProcess.startCamera(getActivity(), fullImageAnalyse, cameraPreviewWrap);
                }
            }
        });
        return  null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}




