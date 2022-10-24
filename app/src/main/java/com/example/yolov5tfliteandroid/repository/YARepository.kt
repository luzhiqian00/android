package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.ServiceCreator
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.api.ApiNet

object YARepository {
    private val service = ServiceCreator.create(ApiNet::class.java)

    @JvmStatic
    public fun postLogin(user:String,pswd:String) = service.postLogin(user,pswd)
    @JvmStatic
    public fun postRegister(user: String,pswd: String,email:String)= service.postRegister(user, pswd, email)
    @JvmStatic
    public fun postEmail(email: String)= service.postEmail(email)
    @JvmStatic
    public fun postChangepwd(name:String,pwd:String)= service.postChangepwd(name,pwd)
}