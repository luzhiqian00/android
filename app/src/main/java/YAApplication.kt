package com.example.yolov5tfliteandroid

import android.app.Application
import android.content.Context

class YAApplication: Application() {
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }
}