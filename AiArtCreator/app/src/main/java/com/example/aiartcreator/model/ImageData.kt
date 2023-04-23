package com.example.aiartcreator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imagedata")
data class ImageData(
    @PrimaryKey(autoGenerate = true)
    val dataId :Int = 0,
    val image :ByteArray,
    val arg1 :String,
    val arg2 :String

)
