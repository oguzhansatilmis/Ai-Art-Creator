package com.example.aiartcreator.usecase

import com.example.aiartcreator.repository.ImageRepositoryImpl
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ImageUseCase @Inject constructor( private val imageRepositoryImpl: ImageRepositoryImpl) {

    suspend fun createImageUseCase(prompt:String) : Flow<Response<ResponseBody>> =
        imageRepositoryImpl.createImage(prompt)


}