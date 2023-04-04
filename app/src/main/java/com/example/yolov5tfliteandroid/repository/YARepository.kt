package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.ServiceCreator
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.api.ApiNet

object YARepository {
    private val service = ServiceCreator.create(ApiNet::class.java)

    @JvmStatic
    public fun postLogin(user:String,pswd:String) = service.postLogin(user,pswd)
    @JvmStatic
    public fun postRegister(user: String,pswd: String,checkpswd:String,email:String)= service.postRegister(user, pswd,checkpswd ,email)
    @JvmStatic
    public fun postEmail(email: String)= service.postEmail(email)
    @JvmStatic
    public fun postChangepwd(name:String,pwd:String,checkpwd: String)= service.postChangepwd(name,pwd,checkpwd)
}