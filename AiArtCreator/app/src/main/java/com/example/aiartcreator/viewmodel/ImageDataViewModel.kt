package com.example.aiartcreator.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiartcreator.database.ImageDataDao
import com.example.aiartcreator.repository.ImageRepository
import com.example.aiartcreator.model.ImageData
import com.example.aiartcreator.network.DezgoApiService
import com.example.aiartcreator.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ImageDataViewModel @Inject constructor(private val apiService: DezgoApiService,private val repository: ImageRepository,private val imageDataDao: ImageDataDao) :ViewModel()
{
        val allImages : Flow<List<ImageData>> =repository.allImages

    suspend fun insert(image : ImageData) = viewModelScope.launch {
        repository.insert(image)
    }
    suspend fun getTextToImage(prompt: String): Flow<Response<ResponseBody>> = flow {
        val response = apiService.getTextToImageAsStream(
            apiKey =Constants.API_KEY,
            prompt = prompt,
            guidance = "7",
            steps = 30,
            sampler = "euler_a",
            upscale = 1,
            negativePrompt = "ugly, tiling, poorly drawn hands, poorly drawn feet, poorly drawn face, out of frame, extra limbs, disfigured, deformed, body out of frame, blurry, bad anatomy, blurred, watermark, grainy, signature, cut off, draft",
            model = "epic_diffusion_1_1"
        )
        emit(response)
    }
}


