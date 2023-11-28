package com.example.aiartcreator.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiartcreator.model.ImageData
import com.example.aiartcreator.model.Result
import com.example.aiartcreator.usecase.ImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ImageDataViewModel @Inject constructor(
    private val imageUseCase: ImageUseCase)
    : ViewModel() {

    private val _imageResponseState = MutableStateFlow<Result<Response<ResponseBody>>>(Result.Loading.create())
    val imageResponseState: StateFlow<Result<Response<ResponseBody>>> = _imageResponseState

     fun fetchData(prompt:String) {

        viewModelScope.launch {
            try {
                imageUseCase.createImageUseCase(prompt = prompt).collect{response->
                    if (response.isSuccessful){
                        _imageResponseState.value =Result.Success(response)
                    }else{
                        _imageResponseState.value = Result.Error("image not created")
                    }

                }
            }catch (e:Exception){
                _imageResponseState.value = Result.Error("image not created $e")
            }
        }

    }
    fun saveImage(imageData: ImageData){
        viewModelScope.launch {
            try {
                imageUseCase.saveImageUseCase(imageData)
            }catch (e:Exception){
                Log.e("e",e.toString())
            }
        }
    }

}


