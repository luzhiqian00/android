package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.example.yolov5tfliteandroid.YAApplication
import com.example.yolov5tfliteandroid.analysis.AppDataBase
import com.example.yolov5tfliteandroid.analysis.ImageDataBase
import com.permissionx.guolindev.dialog.getPermissionMapOnQ
import kotlinx.android.synthetic.main.activity_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EMImageView (context:Context,attrs:AttributeSet):
    androidx.appcompat.widget.AppCompatImageView(context,attrs){

    var array = arrayOfNulls<Double>(2)

     fun draw(canvas: Canvas?, id:Integer):Array<Double?>{
        super.draw(canvas)

        val paintRect= Paint()
        paintRect.color=Color.rgb(108,10,13)
        paintRect.strokeWidth=1f
        paintRect.style=Paint.Style.STROKE

        // 字体画笔
        val textPain = Paint()
        textPain.textSize = 30f
        textPain.color = Color.RED
        textPain.style = Paint.Style.FILL



        GlobalScope.launch(Dispatchers.IO) {
            val userDao= AppDataBase.getDatabase(YAApplication.context).imageDataBaseDao();//如何使用ROOM
            val imageList:List<ImageDataBase> =userDao.loadImageDataBaseImageNumber("image${id}.png");

            for(data in imageList){
                canvas?.apply {
                    drawRect(
                        (data.top).toFloat(), (480-data.right*0.75).toFloat(),
                        (data.bottom).toFloat(), (480-data.left*0.75).toFloat(),paintRect)
                    drawText(data.label+":"+String.format("%.2f",data.confidence),
                        (data.top).toFloat(),(480-data.right*0.75).toFloat(),textPain)
                }
            }
            array[0]=imageList[0].latitude
            array[1]=imageList[0].longitude
    }
    return array
} }