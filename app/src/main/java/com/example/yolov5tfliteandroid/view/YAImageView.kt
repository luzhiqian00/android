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

class YAImageView (context:Context,attrs:AttributeSet):
    androidx.appcompat.widget.AppCompatImageView(context,attrs) {

    fun draw(canvas: Canvas?, mImageDataBase: ImageDataBase) {
        super.draw(canvas)

        val paintRect = Paint()
        paintRect.color = Color.rgb(108, 10, 13)
        paintRect.strokeWidth = 1f
        paintRect.style = Paint.Style.STROKE

        // 字体画笔
        val textPain = Paint()
        textPain.textSize = 30f
        textPain.color = Color.RED
        textPain.style = Paint.Style.FILL

        canvas?.apply {
            drawRect(
                (mImageDataBase.top).toFloat(),
                (480 - mImageDataBase.right * 0.75).toFloat(),
                (mImageDataBase.bottom).toFloat(),
                (480 - mImageDataBase.left * 0.75).toFloat(),
                paintRect
            )
            drawText(
                mImageDataBase.label + ":" + String.format("%.2f", mImageDataBase.confidence),
                (mImageDataBase.top).toFloat(),
                (480 - mImageDataBase.right * 0.75).toFloat(),
                textPain
            )
        }
    }
}