package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean

import com.google.gson.annotations.SerializedName

/**
 * 用户登陆的响应内容
 */

class LoginBean(@SerializedName("RequestName") val RequestName:String,
                @SerializedName("res") val res:String)