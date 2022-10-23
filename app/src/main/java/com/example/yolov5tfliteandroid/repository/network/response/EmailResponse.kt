package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean.EmailBean
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean.LoginBean

class EmailResponse(msg: String, code: String, data: EmailBean):
    BaseResponse<EmailBean>(msg, code, data)