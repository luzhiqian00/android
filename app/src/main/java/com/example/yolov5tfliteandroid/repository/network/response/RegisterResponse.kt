package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean.RegisterBean

class RegisterResponse(msg: String, code: String, data: RegisterBean):
    BaseResponse<RegisterBean>(msg, code, data)