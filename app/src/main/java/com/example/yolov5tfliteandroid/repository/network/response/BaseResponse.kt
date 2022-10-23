package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response

import com.google.gson.annotations.SerializedName

/**
 * 网络响应的公共结构
 */
open class BaseResponse<T>(@SerializedName("success") val msg:String,
                           @SerializedName("Code") val code:String,
                           @SerializedName("state") val data:T)