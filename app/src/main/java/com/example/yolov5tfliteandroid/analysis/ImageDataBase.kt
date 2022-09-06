package com.example.yolov5tfliteandroid.analysis

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class ImageDataBase(val context: Context,name:String,version:Int):
            SQLiteOpenHelper(context,name,null,version){

    private val createImage="create table ImageStore("+
            "id integer primary key autoincrement,"+
            "labelName String,"+
            "labelScore Float,"+
            "confidence Float,"+
            "location RectF)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createImage)
        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
    fun put_together(labelName: String,labelSore:Float,confidence:Float):ContentValues{
        val values=ContentValues().apply {
            put("labelName",labelName)
            put("labelScore",labelSore)
            put("confidence",confidence)
        }
        return values
    } }