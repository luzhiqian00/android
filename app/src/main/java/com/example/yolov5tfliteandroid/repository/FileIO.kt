package com.example.yolov5tfliteandroid.repository

import android.content.Context
import android.graphics.*
import androidx.camera.core.CameraX.isInitialized
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
import java.util.ArrayList

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
}