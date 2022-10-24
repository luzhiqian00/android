package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean

import com.google.gson.annotations.SerializedName

/**
 * 邮件结果返回
 */

class EmailBean(@SerializedName("RequestEmail") val RequestEmail:String,
    @SerializedName("res") val res:String)