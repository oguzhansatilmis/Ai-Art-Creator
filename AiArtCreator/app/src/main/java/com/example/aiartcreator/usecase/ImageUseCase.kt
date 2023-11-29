package com.example.aiartcreator.usecase

import android.media.Image
import com.example.aiartcreator.model.ImageData
import com.example.aiartcreator.repository.ImageRepositoryImpl
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ImageUseCase @Inject constructor( private val imageRepositoryImpl: ImageRepositoryImpl) {




    suspend fun createImageUseCase(prompt:String) : Flow<Response<ResponseBody>> =
        imageRepositoryImpl.createImage(prompt)


    suspend fun saveImageUseCase(imageData: ImageData){
        imageRepositoryImpl.saveImage(imageData)
    }

     suspend fun getImageDatabase() :Flow<List<ImageData>>{

        return imageRepositoryImpl.getLocalImageList()
    }

}