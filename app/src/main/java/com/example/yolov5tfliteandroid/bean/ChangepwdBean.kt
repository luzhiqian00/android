package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.bean

import com.google.gson.annotations.SerializedName

/**
 * 修改密码结果
 */

class ChangepwdBean(@SerializedName("RequestName") val RequestEmail:String,
                @SerializedName("res") val res:String)
