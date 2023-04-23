package com.example.aiartcreator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aiartcreator.model.ImageData
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insert(imageData: ImageData)

    @Query("SELECT* FROM imagedata")
    fun getImageDataAll() : Flow<List<ImageData>>
}