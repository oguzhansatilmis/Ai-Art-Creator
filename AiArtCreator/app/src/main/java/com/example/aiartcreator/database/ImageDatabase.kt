package com.example.aiartcreator.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aiartcreator.model.ImageData


@Database(entities = [ImageData::class], version = 1)
abstract class ImageDatabase :RoomDatabase(){

    abstract fun imagedatadao() : ImageDataDao
/*
 companion object{

        @Volatile
        private var instance: ImageDatabase? = null

        fun getDatabase(contex :Context) : ImageDatabase {
            return instance ?: synchronized(this){
                val database = Room.databaseBuilder(
                    contex.applicationContext,
                    ImageDatabase::class.java,
                "image_database"
                ).build()
                instance = database
                database
            }
        }
    }
 */

}