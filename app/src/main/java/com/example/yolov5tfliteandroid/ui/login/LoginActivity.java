package com.example.yolov5tfliteandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.yolov5tfliteandroid.web.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userName;
    private EditText passWord;
    private Button loginButton;
    private Button signUpButton;
    boolean pendingCollapseKeywordInLogin = false;
    View focusedViewInLogin;
    //String test=network.Server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    private void hideInputMethod(LoginActivity context) {
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(focusedViewInLogin.getWindowToken(), 0);
        }
    }
    private void InitView(){
        userName =findViewById(R.id.UserNameEdit);
        passWord =findViewById(R.id.PassWordEdit);
        loginButton =findViewById(R.id.LoginButton);
        signUpButton =findViewById(R.id.SignUpButton);
    }
    private  void InitEvent(){
        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.LoginButton:
                String strUserName = userName.getText().toString();
                String strPassWord = passWord.getText().toString();
                //TODO 账号密码处理
                final Map<String, String> map = new HashMap<>();
                map.put("name", strUserName);
                map.put("pwd", strPassWord);
                new Thread(() -> {
                    Looper.prepare();
                    network.ppmapPOST(network.Server + "php/connect.php", map, (ppansJson) -> {
                        try {
                            if (ppansJson.getInt("res") == 1) {//登录成功
                                Toast.makeText(getApplicationContext(), "登录成功",
                                        Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(LoginActivity.this,Bottom.class);
                                intent.putExtra( "name", strUserName );
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "登录失败：账号或密码错误 " ,
                                        Toast.LENGTH_LONG).show();
                                //Intent intent=new Intent(LoginActivity.this,Bottom.class);
                                //startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Looper.loop();

                    });
                }).start();
            break;
        case R.id.SignUpButton:
            // 跳转到注册界面
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        //android.os.Process.killProcess(android.os.Process.myPid());
    }
}
