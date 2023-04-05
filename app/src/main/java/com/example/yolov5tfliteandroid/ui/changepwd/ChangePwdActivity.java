package com.example.yolov5tfliteandroid.ui.changepwd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yolov5tfliteandroid.Bottom;
import com.example.yolov5tfliteandroid.CacheActivity;
import com.example.yolov5tfliteandroid.R;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.YARepository;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.ChangepwdResponse;
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.LoginResponse;
import com.example.yolov5tfliteandroid.ui.login.LoginActivity;
import com.example.yolov5tfliteandroid.web.network;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwdActivity extends AppCompatActivity implements View.OnClickListener {
    boolean pendingCollapseKeywordInLogin = false;
    private Button ChangePwd;
    private EditText OldPwd;
    private EditText NewPwd;
    private EditText FirmPwd;
    private String pwd;
    private String name;
    View focusedViewInLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
        if (!CacheActivity.activityList.contains(ChangePwdActivity.this)) {
            CacheActivity.addActivity(ChangePwdActivity.this);
        }
        InitView();
        InitEvent();
    }
    private void InitView(){
        ChangePwd=findViewById(R.id.ChangePwdButton);
        OldPwd=findViewById(R.id.OldPwd);
        NewPwd=findViewById(R.id.PassWordEdit);
        FirmPwd=findViewById(R.id.PassWordEdit2);
    }
    private void InitEvent(){
        ChangePwd.setOnClickListener(this);
        Intent intent = this.getIntent();
        pwd=intent.getStringExtra("pwd");
        name=intent.getStringExtra("name");
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ChangePwdButton:
                changepassword();
                break;
        }
    }
    private void changepassword(){
        String newp = NewPwd.getText().toString();
        String newp2 = FirmPwd.getText().toString();
        String oldp = OldPwd.getText().toString();
        //CacheActivity.finishActivity();
        if(!newp.equals(newp2)){
            Toast.makeText(getApplicationContext(), "两次密码输入不一致",
                    Toast.LENGTH_LONG).show();
        }else if(oldp.equals(pwd)){
            YARepository.postChangepwd(name,oldp,newp).enqueue(new Callback<ChangepwdResponse>() {
                @Override
                public void onResponse(Call<ChangepwdResponse> call, Response<ChangepwdResponse> response) {
                    // 响应失败
                    long res = response.body().getData().getRes();
                    if (res==1) {//
                        Toast.makeText(getApplicationContext(), "修改成功",
                                Toast.LENGTH_LONG).show();
                        CacheActivity.finishActivity();
                    }else{
                        Toast.makeText(getApplicationContext(), "修改失败 " ,
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ChangepwdResponse> call, Throwable t) {
                    // 响应失败
                    t.printStackTrace();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "原密码不正确 " ,Toast.LENGTH_LONG).show();
        }
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

    private void hideInputMethod(ChangePwdActivity context) {
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(focusedViewInLogin.getWindowToken(), 0);
        }
    }
}
