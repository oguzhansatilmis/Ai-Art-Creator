package com.example.aiartcreator.model

sealed class Result<out T>{

    data class Success<out T>(val data :T) : Result <T>()
    data class Error(val message :String?,val data :Nothing?= null) :Result<Nothing>()

    class Loading<out T> : Result<T>() {
        companion object {
            fun <T> create(): Loading<T> {
                return Loading()
            }
        }
    }
}
