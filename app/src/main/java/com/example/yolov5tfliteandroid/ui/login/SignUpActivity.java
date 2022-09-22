package com.example.yolov5tfliteandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yolov5tfliteandroid.Bottom;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.web.network;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        InitView();
        InitEvent();
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
                } else if(yanzhengma!=Integer.parseInt(email2.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "验证码不正确！", Toast.LENGTH_SHORT).show();
                } else {
                    final Map<String, String> map = new HashMap<>();
                    map.put("name", strUserName);
                    map.put("pwd", strPassWord);
                    map.put("email",strPhoneNumber);
                    new Thread(() -> {
                        Looper.prepare();
                        network.ppmapPOST(network.Server + "php/regist.php", map, (ppansJson) -> {
                            try {
                                int res=ppansJson.getInt("res");
                                if (res == 2) {//登录成功
                                    Toast.makeText(getApplicationContext(), "注册成功！",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if(res==1) {
                                    Toast.makeText(getApplicationContext(), "邮箱已被注册！" ,
                                            Toast.LENGTH_LONG).show();
                                    //Intent intent=new Intent(LoginActivity.this,Bottom.class);
                                    //startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), "用户名已被注册！" ,
                                            Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Looper.loop();

                        });
                    }).start();
                }
                break;
            case R.id.yanzhengmaButton:
                new Thread(() -> {
                    Looper.prepare();
                    network.GET(network.Server + "php/email.php",  (yanzhengJson) -> {
                        try {
                            if (yanzhengJson.getInt("res") != 0) {//发送成功返回验证码
                                Toast.makeText(getApplicationContext(), "邮件发送成功！",
                                        Toast.LENGTH_LONG).show();
                                yanzhengma=yanzhengJson.getInt("res") ;
                            } else {
                                Toast.makeText(getApplicationContext(), "邮件发送失败！ " ,
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Looper.loop();
                    });
                }).start();
                break;
            case R.id.BackLoginButton:
                // 跳转到登录界面
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
        }
    }
}
