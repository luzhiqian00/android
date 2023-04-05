package com.example.yolov5tfliteandroid.analysis

import androidx.room.*

@Dao
interface ImageDataBaseDao {
    @Insert
    fun insertImageData(imageDataBase: ImageDataBase):Long

    @Update
    fun updateImageData(newImageDataBase: ImageDataBase)

    @Query("select * from ImageDataBase")
    fun loadAllImageData():List<ImageDataBase>

    @Query("select * from ImageDataBase where imageName=:imageName")
    fun loadImageDataBaseImageNumber(imageName:String):List<ImageDataBase>

    @Query("select * from ImageDataBase where finish_status=:status")
    fun loadImageDataBaseFinishStatus(status:Int):List<ImageDataBase>

    @Query("delete from ImageDataBase where imageName=:imageName")
    fun deleteByName(imageName :String)

}