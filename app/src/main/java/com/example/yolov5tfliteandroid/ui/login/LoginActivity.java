package com.example.yolov5tfliteandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yolov5tfliteandroid.Bottom;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.YARepository;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.LoginResponse;
import com.example.yolov5tfliteandroid.utils.VerifyCode;
import com.example.yolov5tfliteandroid.web.network;
import com.permissionx.guolindev.PermissionX;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//将验证码用图片的形式显示出来
//RegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
//        realCode = Code.getInstance().getCode().toLowerCase();
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userName;
    private EditText passWord;
    private EditText VerifyEdit;
    private Button loginButton;
    private Button signUpButton;
    private ImageView yanzheng;
    private CheckBox RememberPwd;
    private String realCode;
    boolean pendingCollapseKeywordInLogin = false;
    View focusedViewInLogin;

    //String test=network.Server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestPermission(this);
        InitView();
        InitEvent();
        yanzheng.setImageBitmap(VerifyCode.getInstance().createBitmap());
        realCode = VerifyCode.getInstance().getCode().toLowerCase();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        focusedViewInLogin = getCurrentFocus();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            pendingCollapseKeywordInLogin = isShouldHideInput(ev);
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (pendingCollapseKeywordInLogin) {
                hideInputMethod(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(MotionEvent event) {
        if (focusedViewInLogin instanceof EditText) {
            int[] location = {0, 0};
            focusedViewInLogin.getLocationInWindow(location);
            boolean t = event.getX() < location[0]
                    || event.getX() > location[0] + focusedViewInLogin.getWidth()
                    || event.getY() < location[1]
                    || event.getY() > location[1] + focusedViewInLogin.getHeight();
            ((EditText) focusedViewInLogin).setCursorVisible(!t);
            return t;
        }
        return false;
    }

    private void hideInputMethod(LoginActivity context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(focusedViewInLogin.getWindowToken(), 0);
        }
    }

    private void InitView() {
        userName = findViewById(R.id.UserNameEdit);
        passWord = findViewById(R.id.PassWordEdit);
        VerifyEdit = findViewById(R.id.VerifyEdit);
        loginButton = findViewById(R.id.LoginButton);
        signUpButton = findViewById(R.id.SignUpButton);
        yanzheng = findViewById(R.id.VerifyImage);
        RememberPwd=findViewById(R.id.RememberPwd);
    }

    private void InitEvent() {
        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        yanzheng.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LoginButton:
                String strUserName = userName.getText().toString();
                String strPassWord = passWord.getText().toString();
                String strVerifyCode = VerifyEdit.getText().toString();
                strVerifyCode = strVerifyCode.toLowerCase(Locale.ROOT);
                //TODO 账号密码处理
                if (strVerifyCode.equals(realCode)) {


                    // 登陆请求

                    YARepository.postLogin(strUserName,strPassWord).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            // 响应成功
                            String res = response.body().getData().getRes();
                            if (res.equals("1")) {//登录成功
                                Toast.makeText(getApplicationContext(), "登录成功",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, Bottom.class);
                                intent.putExtra("name", strUserName);
                                intent.putExtra("pwd", strPassWord);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "登录失败：账号或密码错误 ", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            // 响应失败
                            t.printStackTrace();
                        }
                    });




//                    final Map<String, String> map = new HashMap<>();
//                    map.put("name", strUserName);
//                    map.put("pwd", strPassWord);
//                    new Thread(() -> {
//                        network.ppmapPOST(network.Server + "php/connect.php", map, (ppansJson) -> {
//                            try {
//                                if (ppansJson.getInt("res") == 1) {//登录成功
//                                    Toast.makeText(getApplicationContext(), "登录成功",
//                                            Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(LoginActivity.this, Bottom.class);
//                                    intent.putExtra("name", strUserName);
//                                    intent.putExtra("pwd", strPassWord);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "登录失败：账号或密码错误 ",
//                                            Toast.LENGTH_LONG).show();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        });
//                    }).start();
                } else
                    Toast.makeText(getApplicationContext(), "登录失败：验证码输入错误 ",
                            Toast.LENGTH_LONG).show();
                break;
            case R.id.SignUpButton:
                // 跳转到注册界面
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.VerifyImage:
                yanzheng.setImageBitmap(VerifyCode.getInstance().createBitmap());
                realCode = VerifyCode.getInstance().getCode().toLowerCase();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        //android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 启动APP后动态获取权限
     * 权限列表:
     * 1. 读
     * 2. 写
     * 3. 相机
     */
    // TODO: 权限失败后情况，如退出APP
    private void requestPermission(Context context) {
        PermissionX.init((FragmentActivity) context)
                .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .onExplainRequestReason((scope, deniedList) -> {
                    scope.showRequestReasonDialog(deniedList, "需要您同意以下权限才能正常使用", "同意", "拒绝");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {

                    } else {
                        Toast.makeText(this, "您拒绝了如下权限：" + deniedList.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
