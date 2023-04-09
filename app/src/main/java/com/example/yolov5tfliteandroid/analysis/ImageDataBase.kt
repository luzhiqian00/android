package com.example.yolov5tfliteandroid.analysis

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity
@Parcelize
data class ImageDataBase(var imageName:String, var label:String,var confidence:Float,
                         var left:Float,var top:Float,var right:Float,var bottom:Float,var finish_status:Int,
                         var createTime:String,var latitude:Double,var longitude:Double,var location:String
): Parcelable
{
    @PrimaryKey(autoGenerate = true)var id=0L       //L代表是Long类型
}