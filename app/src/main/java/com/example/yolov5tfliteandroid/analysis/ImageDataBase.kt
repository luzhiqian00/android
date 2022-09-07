package com.example.yolov5tfliteandroid.analysis

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageDataBase(var label:String,var confidence:String,
                         var left:String,var top:String,var right:String,var bottom:String){
    @PrimaryKey(autoGenerate = true)var id=0L       //L代表是Long类型
}
