package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.ServiceCreator
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.api.ApiNet

object YARepository {
    private val service = ServiceCreator.create(ApiNet::class.java)

    @JvmStatic
    public fun postLogin(user:String,pswd:String) = service.postLogin(user,pswd)
}