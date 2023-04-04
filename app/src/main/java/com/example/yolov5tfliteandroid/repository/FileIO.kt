package com.example.yolov5tfliteandroid.repository

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.yolov5tfliteandroid.YAApplication
import com.example.yolov5tfliteandroid.analysis.AppDataBase.Companion.getDatabase
import com.example.yolov5tfliteandroid.analysis.ImageDataBase
import com.example.yolov5tfliteandroid.utils.Recognition
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.sql.Date
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

    @RequiresApi(Build.VERSION_CODES.O)
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

                //获得当前时间
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
                val date = Date(System.currentTimeMillis())
                val dateStr = simpleDateFormat.format(date)
                //val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a"))
                val a = ImageDataBase(
                    "image$number.png",
                    res.labelName,
                    res.confidence,
                    res.location.left,
                    res.location.top,
                    res.location.right,
                    res.location.bottom,
                    0,
                    dateStr,
                    YAApplication.latitude[0],
                    YAApplication.latitude[1]
                )//储存时是在W=640 和H=640的条件下的图像
                //上面最下面两个0 应该是精度维度这里先用0替代，出了点问题
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

//    @JvmStatic
//    fun selectImageAll(): Any? {
//        var res = MutableLiveData<List<ImageDataBase>>();
//        GlobalScope.launch(Dispatchers.IO) {
//            val userDao = getDatabase(YAApplication.context).imageDataBaseDao()
//            val qwq = userDao.loadAllImageData();
//            res.postValue(qwq);
//            System.out.println(res.value);
//        }
//        sleep(2000)
//        var ans = res.value;
//        System.out.println(res.value);
//        return ans;
//    }


}