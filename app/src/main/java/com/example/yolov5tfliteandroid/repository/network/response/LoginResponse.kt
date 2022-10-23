package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean.LoginBean

class LoginResponse(msg: String, code: String, data: LoginBean):
    BaseResponse<LoginBean>(msg, code, data)