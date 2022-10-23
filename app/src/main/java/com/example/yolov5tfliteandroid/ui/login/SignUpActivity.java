package com.example.yolov5tfliteandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yolov5tfliteandroid.Bottom;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.YARepository;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.EmailResponse;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.LoginResponse;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.RegisterResponse;
import com.example.yolov5tfliteandroid.web.network;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userName;
    private EditText passWord;
    private EditText passWordAgain;
    private EditText email;
    private EditText email2;
    private Button signUpButton;
    private Button backLoginButton;
    private Button yangzhengButton;
    private int yanzhengma=0;
    boolean pendingCollapseKeywordInLogin = false;
    View focusedViewInLogin;
    private void countDownTime() {
        //用安卓自带的CountDownTimer实现

        CountDownTimer mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Log.i(TAG, "millisUntilFinished: " + millisUntilFinished);
                yangzhengButton.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                yangzhengButton.setEnabled(true);
                yangzhengButton.setText("发送验证码");
                cancel();
            }
        };
        mTimer.start();
        yangzhengButton.setEnabled(false);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        InitView();
        InitEvent();
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

    private void hideInputMethod(SignUpActivity context) {
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(focusedViewInLogin.getWindowToken(), 0);
        }
    }

    private void InitView(){
        userName = findViewById(R.id.UserNameEdit);
        passWord = findViewById(R.id.PassWordEdit);
        passWordAgain = findViewById(R.id.PassWordAgainEdit);
        email = findViewById(R.id.EmailEdit);
        email2=findViewById(R.id.Email2Edit);
        signUpButton = findViewById(R.id.SignUpButton);
        backLoginButton = findViewById(R.id.BackLoginButton);
        yangzhengButton=findViewById(R.id.yanzhengmaButton);
    }

    private void InitEvent(){
        signUpButton.setOnClickListener(this);
        backLoginButton.setOnClickListener(this);
        yangzhengButton.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.SignUpButton:
                String strUserName = userName.getText().toString();
                String strPassWord = passWord.getText().toString();
                String strPassWordAgain = passWordAgain.getText().toString();
                String strPhoneNumber = email.getText().toString();
                //注册格式粗检
                if (strUserName.length() > 10) {
                    Toast.makeText(SignUpActivity.this, "用户名长度必须小于10！", Toast.LENGTH_SHORT).show();
                } else if (strUserName.length() < 4) {
                    Toast.makeText(SignUpActivity.this, "用户名长度必须大于4！", Toast.LENGTH_SHORT).show();
                } else if (strPassWord.length() > 16) {
                    Toast.makeText(SignUpActivity.this, "密码长度必须小于16！", Toast.LENGTH_SHORT).show();
                } else if (strPassWord.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "密码长度必须大于6！", Toast.LENGTH_SHORT).show();
                } else if (!strPassWord.equals(strPassWordAgain)) {
                    Toast.makeText(SignUpActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                } else if (!strPhoneNumber.contains("@")) {
                    Toast.makeText(SignUpActivity.this, "邮箱格式不正确！", Toast.LENGTH_SHORT).show();
                } else if(yanzhengma==0){
                    Toast.makeText(SignUpActivity.this, "请先获取验证码！", Toast.LENGTH_SHORT).show();
                } else if(yanzhengma!=Integer.parseInt(email2.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "验证码不正确！", Toast.LENGTH_SHORT).show();
                } else {
                    YARepository.postRegister(strUserName,strPassWord,strPhoneNumber).enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            // 响应成功

                            String res = response.body().getData().getRes();
                            if (res.equals("2")) {
                                Toast.makeText(getApplicationContext(), "注册成功！",
                                        Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else if(res.equals("1")) {
                                Toast.makeText(getApplicationContext(), "邮箱已被注册！" , Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "用户名已被注册！" , Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            // 响应失败
                            t.printStackTrace();
                        }
                    });
                }
                break;
            case R.id.yanzhengmaButton:
                countDownTime();
                String Email = email.getText().toString();
                YARepository.postEmail(Email).enqueue(new Callback<EmailResponse>() {
                    @Override
                    public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                        String res=response.body().getData().getRes();
                        yanzhengma=Integer.parseInt(res);
                        if(yanzhengma!=0){
                            Toast.makeText(getApplicationContext(), "邮件发送成功！",
                                        Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "邮件发送失败！",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<EmailResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
            case R.id.BackLoginButton:
                // 跳转到登录界面
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
        }
    }
}
