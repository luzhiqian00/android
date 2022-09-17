package com.example.yolov5tfliteandroid.com.example.yolov5tfliteandroid.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.example.yolov5tfliteandroid.YAApplication
import com.example.yolov5tfliteandroid.analysis.AppDataBase
import com.example.yolov5tfliteandroid.analysis.ImageDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EMImageView (context:Context,attrs:AttributeSet):androidx.appcompat.widget.AppCompatImageView(context,attrs){

    override fun draw(canvas: Canvas?) {
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
            val imageList:List<ImageDataBase> =userDao.loadImageDabaBaseImageNumber("image8.png");
            for(data in imageList){
                canvas?.apply {
                    drawRect(
                        (640-data.bottom*0.65).toFloat(), (data.left).toFloat(),
                        (640-data.top*0.55).toFloat(), (data.right).toFloat(),paintRect)
                    drawText(data.label+":"+String.format("%.2f",data.confidence),
                        (640-data.bottom*0.65).toFloat(),data.left,textPain)
                }
            }
        }
    }
}