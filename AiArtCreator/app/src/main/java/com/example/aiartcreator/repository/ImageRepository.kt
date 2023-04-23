package com.example.aiartcreator.repository

import androidx.lifecycle.LiveData
import com.example.aiartcreator.database.ImageDataDao
import com.example.aiartcreator.model.ImageData
import com.example.aiartcreator.network.DezgoApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imageDataDao: ImageDataDao,private val apiService: DezgoApiService) {

    val allImages :Flow<List<ImageData>> =imageDataDao.getImageDataAll()

   
    suspend fun insert(imageData: ImageData){
        imageDataDao.insert(imageData)
    }

}