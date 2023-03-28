package com.example.yolov5tfliteandroid

import android.app.Application
import android.content.Context
import java.io.File

class YAApplication: Application() {
    companion object{
        lateinit var context: Context
        lateinit var fDir: String
        lateinit var latitude:DoubleArray //0位置是维度，1位置是经度


    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        fDir = context.filesDir.toString() ;
    }



}