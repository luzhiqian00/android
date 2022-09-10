package com.example.yolov5tfliteandroid.repository

import android.graphics.Bitmap
import com.example.yolov5tfliteandroid.YAApplication
import com.example.yolov5tfliteandroid.analysis.AppDataBase
import com.example.yolov5tfliteandroid.analysis.AppDataBase.Companion.getDatabase
import com.example.yolov5tfliteandroid.analysis.ImageDataBase
import com.example.yolov5tfliteandroid.analysis.ImageDataBaseDao
import com.example.yolov5tfliteandroid.utils.Recognition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

object FileIO {
    @JvmStatic
    fun saveImage(number: Int,imageBitmap:Bitmap){
        GlobalScope.launch(Dispatchers.IO) {
            val file = File(YAApplication.context.filesDir.toString() + "image" + number.toString() + ".png")
            val fileOutputStream= FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        }
    }

    @JvmStatic
    fun saveRes(number: Int,res:Recognition){
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = getDatabase(YAApplication.context).imageDataBaseDao()
            val a = ImageDataBase(
                "image$number.png",
                res.labelName,
                res.confidence,
                res.location.left,
                res.location.top,
                res.location.right,
                res.location.bottom
            )
            userDao.insertImageData(a)
        }
    }
}