package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.repository.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 根据YAApplication的配置
 * 创建Retrofit
 */
object ServiceCreator {
    private const val BASE_URL= "http://bjtu.qingyouchen.top:8080/"
    //private const val BASE_URL= "http://192.168.31.114:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}