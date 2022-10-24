package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean

import com.google.gson.annotations.SerializedName

/**
 * 用户登陆返回data
 */

class LoginBean(@SerializedName("RequestName") val RequestName:String,
                @SerializedName("res") val res:String)