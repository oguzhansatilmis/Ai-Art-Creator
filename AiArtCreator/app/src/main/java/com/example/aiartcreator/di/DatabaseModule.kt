package com.example.aiartcreator.di

import android.content.Context
import androidx.room.Room
import com.example.aiartcreator.database.ImageDataDao
import com.example.aiartcreator.database.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton

    fun provideChannelDao(imageDatabase: ImageDatabase): ImageDataDao{
        return imageDatabase.imagedatadao()
    }
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ImageDatabase{
        return Room.databaseBuilder(
            appContext,
            ImageDatabase::class.java,
            "imagedata"
        ).build()
    }
}