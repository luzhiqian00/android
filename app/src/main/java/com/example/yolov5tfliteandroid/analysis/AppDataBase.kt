package com.example.yolov5tfliteandroid.analysis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(version = 1, entities = [ImageDataBase::class], exportSchema = false)
abstract class AppDataBase:RoomDatabase(){
    abstract fun imageDataBaseDao():ImageDataBaseDao

    companion object{
        private var instance:AppDataBase?=null

        @JvmStatic
        fun getDatabase(context: Context):AppDataBase{
            instance?.let { return it }
            return Room.databaseBuilder(context.applicationContext, AppDataBase::class.java,"app_database.db")
                .build().apply {
                    instance=this
                }
        }
    }

}