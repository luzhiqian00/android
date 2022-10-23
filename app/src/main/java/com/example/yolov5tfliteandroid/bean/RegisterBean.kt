package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean

import com.google.gson.annotations.SerializedName

/**
 * 用户注册的发送内容
 */
class RegisterBean(@SerializedName("RequestName") val RequestName:String,
                @SerializedName("res") val res:String)