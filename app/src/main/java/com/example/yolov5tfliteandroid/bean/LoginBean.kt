package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean

import com.google.gson.annotations.SerializedName

/**
 * 用户登陆返回data
 */

class LoginBean(@SerializedName("userAccount") val userAccount:String,
                @SerializedName("id") val id:String)