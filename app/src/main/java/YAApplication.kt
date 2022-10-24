package com.example.yolov5tfliteandroid

import android.app.Application
import android.content.Context
import java.io.File

class YAApplication: Application() {
    companion object{
        lateinit var context: Context
        lateinit var fDir: String
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        fDir = context.filesDir.toString() + "/image";
    }
}