package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean.EmailBean

class EmailResponse(msg: String, code: String, data: EmailBean):
    BaseResponse<EmailBean>(msg, code, data)