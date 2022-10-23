package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean.ChangepwdBean

class ChangepwdResponse(msg: String, code: String, data: ChangepwdBean):
    BaseResponse<ChangepwdBean>(msg, code, data)
