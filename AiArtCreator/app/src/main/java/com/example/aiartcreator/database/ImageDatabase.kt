package com.example.aiartcreator.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aiartcreator.model.ImageData


@Database(entities = [ImageData::class], version = 1)
abstract class ImageDatabase :RoomDatabase(){

    abstract fun imagedatadao() : ImageDataDao

}