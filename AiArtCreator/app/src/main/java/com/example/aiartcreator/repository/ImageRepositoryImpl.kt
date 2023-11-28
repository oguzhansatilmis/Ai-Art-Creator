package com.example.aiartcreator.repository

import android.util.Log
import com.example.aiartcreator.database.ImageDataDao
import com.example.aiartcreator.model.ImageData
import com.example.aiartcreator.network.DezgoApiService
import com.example.aiartcreator.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import retrofit2.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dezgoApiService: DezgoApiService,
    private val imageDataDao: ImageDataDao
) :ImageRepository {
    override suspend fun createImage(prompt: String): Flow<Response<ResponseBody>> {

        return flow {

            val response = dezgoApiService.getTextToImageAsStream(
                apiKey = Constants.API_KEY,
                prompt = prompt,
                guidance = "7",
                steps = 30,
                sampler = "euler_a",
                upscale = 1,
                negativePrompt = "ugly, tiling, poorly drawn hands, poorly drawn feet, poorly drawn face, out of frame, extra limbs, disfigured, deformed, body out of frame, blurry, bad anatomy, blurred, watermark, grainy, signature, cut off, draft",
                model = "epic_diffusion_1_1"
            )

            if (response.isSuccessful){
                emit(response)
            }
            else{
                Log.e("Repository response error", "${response.code()}")
                emit(response)
            }
        }.catch { e ->
            Log.e("error","$e")
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveImage(imageData: ImageData) {
       imageDataDao.saveImage(imageData = imageData)
    }

}