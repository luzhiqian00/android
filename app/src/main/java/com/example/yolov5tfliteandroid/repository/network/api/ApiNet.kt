package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.api

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.EmailResponse
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.LoginResponse
import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiNet {

    /**
     * 登陆相关
     */
    @FormUrlEncoded
    @POST("php/connect.php")
    fun postLogin(
        @Field("user") user:String,
        @Field("pwd") pswd:String
    ):Call<LoginResponse>

    /**
     * 注册相关
     */
    @FormUrlEncoded
    @POST("php/regist.php")
    fun postRegister(
        @Field("user") user:String,
        @Field("pwd") pswd:String,
        @Field("email") email:String
    ):Call<RegisterResponse>

    /**
     * 发送邮件相关
     */
    @FormUrlEncoded
    @POST("php/email.php")
    fun postRegister(
        @Field("email") email:String
    ):Call<EmailResponse>

}