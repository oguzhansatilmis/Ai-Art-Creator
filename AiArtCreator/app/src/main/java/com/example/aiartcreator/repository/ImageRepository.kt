package com.example.aiartcreator.repository

import com.example.aiartcreator.model.ImageData
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

interface ImageRepository {

    suspend fun createImage(prompt:String) : Flow<Response<ResponseBody>>
    suspend fun saveImage(imageData: ImageData)


}