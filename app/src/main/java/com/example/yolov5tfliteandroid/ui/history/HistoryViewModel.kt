package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.ui.history

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yolov5tfliteandroid.YAApplication
import com.example.yolov5tfliteandroid.analysis.AppDataBase
import com.example.yolov5tfliteandroid.analysis.ImageDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class HistoryViewModel():ViewModel() {


    companion object {
        private const val TAG = "HistoryViewModel"
    }

    var mImageDataBase: MutableLiveData<ImageDataBase> = MutableLiveData()

    fun getImageDataBase(id:Integer){
        GlobalScope.launch(Dispatchers.IO) {
            val userDao= AppDataBase.getDatabase(YAApplication.context).imageDataBaseDao();//如何使用ROOM
            val imageList:List<ImageDataBase> =userDao.loadImageDabaBaseImageNumber("image${id}.png");
            mImageDataBase.postValue(imageList[0])
        }
    }
}