package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response

import com.google.gson.annotations.SerializedName

/**
 * 网络响应的公共结构
 */
open class BaseResponse<T>(@SerializedName("msg") val msg:String,
                           @SerializedName("code") val code:String,
                           @SerializedName("data") val data:T)