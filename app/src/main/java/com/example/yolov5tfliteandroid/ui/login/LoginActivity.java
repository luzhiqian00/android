package com.example.yolov5tfliteandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    //String test=network.Server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitView();
        InitEvent();
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
                    JSONObject ansJson = network.mapPOST(network.Server + "connect.php", map);
                    try {
                        if (ansJson.getInt("res") == 1) {//登录成功
                            Toast.makeText(getApplicationContext(), "登录成功",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "登录失败：" + ansJson.getInt("res") + "，可能的原因：" + ansJson.getString("reason"),
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }).start();
/*                if (strUserName.equals("123456") && strPassWord.equals("123456")) {
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, Bottom.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "请输入正确的用户名或密码！", Toast.LENGTH_SHORT).show();
                }*/
                break;
            case R.id.SignUpButton:
                // 跳转到注册界面
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
        }
    }
}
