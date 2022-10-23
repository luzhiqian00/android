package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.api

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.LoginResponse
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

}