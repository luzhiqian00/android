package com.example.yolov5tfliteandroid

import android.app.Application
import android.content.Context

class YAApplication: Application() {
    companion object {
        lateinit var context: Context
 /*       fun checkInit():Boolean{
            return this::context.isInitialized
        }*/
    }

 override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}