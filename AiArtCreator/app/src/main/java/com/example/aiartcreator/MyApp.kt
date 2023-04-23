package com.example.aiartcreator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp  :Application(){

    override fun onCreate() {
        super.onCreate()

    }
}