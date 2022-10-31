package com.example.yolov5tfliteandroid.repository

import android.graphics.*
import com.example.yolov5tfliteandroid.YAApplication
import com.example.yolov5tfliteandroid.analysis.AppDataBase.Companion.getDatabase
import com.example.yolov5tfliteandroid.analysis.ImageDataBase
import com.example.yolov5tfliteandroid.utils.Recognition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.sql.Timestamp
import java.text.SimpleDateFormat

object FileIO {
    @JvmStatic
    fun saveImage(number: Int,imageBitmap:Bitmap){
        GlobalScope.launch(Dispatchers.IO) {
            val file = File(YAApplication.context.filesDir.toString() + "/image" + number.toString() + ".png")
            val fileOutputStream= FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        }
    }

    @JvmStatic
    fun saveRes(number: Int,recognitions :ArrayList<Recognition>,
                modelToPreviewTransform:Matrix,boxPaint: Paint,cropCanvas: Canvas,textPain:Paint) {
        GlobalScope.launch(Dispatchers.IO) {
            for (res in recognitions)
            {
                var location = res.getLocation()
                var label = res.getLabelName ()
                var confidence = res.getConfidence()
                modelToPreviewTransform.mapRect(location)
                cropCanvas.drawRect(location, boxPaint)
                cropCanvas.drawText(
                    label + ":" + String.format("%.2f", confidence),
                    location.left,
                    location.top,
                    textPain
                )
                var time = System.currentTimeMillis()
                var timestamp = Timestamp(time)
                val strn: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time)

                val userDao = getDatabase(YAApplication.context).imageDataBaseDao()
                val a = ImageDataBase(
                    "image$number.png",
                    res.labelName,
                    res.confidence,
                    res.location.left,
                    res.location.top,
                    res.location.right,
                    res.location.bottom,
                    strn
                )//储存时是在W=640 和H=640的条件下的图像
                userDao.insertImageData(a)
            }
        }
    }

    @JvmStatic
    fun deleteImage(imageName:String){
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = getDatabase(YAApplication.context).imageDataBaseDao()
            userDao.deleteByName(imageName)
        }
    }
}