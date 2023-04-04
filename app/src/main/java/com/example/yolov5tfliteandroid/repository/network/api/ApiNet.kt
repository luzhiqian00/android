package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.api

import com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network.response.ChangepwdResponse
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
    @POST("api/user/login")
    fun postLogin(
        @Field("userAccount") user:String,
        @Field("userPassword") pswd:String
    ):Call<LoginResponse>

    /**
     * 注册相关
     */
    @FormUrlEncoded
    @POST("api/user/register")
    fun postRegister(
        @Field("userAccount") user:String,
        @Field("userPassword") pswd:String,
        @Field("checkPassword") checkpswd:String,
        @Field("email") email:String
    ):Call<RegisterResponse>

    /**
     * 发送邮件相关
     */
    @FormUrlEncoded
    @POST("api/user/email")
    fun postEmail(
        @Field("to") email:String
    ):Call<EmailResponse>


    /**
     * 修改密码相关
     */
    @FormUrlEncoded
    @POST("api/user/changepwd")
    fun postChangepwd(
        @Field("userAccount") name:String,
        @Field("userPassword") pwd:String,
        @Field("newPassword") checkpwd:String,
    ):Call<ChangepwdResponse>
}