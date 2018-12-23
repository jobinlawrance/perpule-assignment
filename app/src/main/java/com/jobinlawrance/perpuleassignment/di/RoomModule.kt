package com.jobinlawrance.perpuleassignment.di

import android.content.Context
import androidx.room.Room
import com.jobinlawrance.perpuleassignment.persistance.AudioDao
import com.jobinlawrance.perpuleassignment.persistance.AudioDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideAudioDatabase(context: Context): AudioDatabase =
        Room.databaseBuilder(context, AudioDatabase::class.java, "audio.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @JvmStatic
    @Provides
    fun provideAudioDao(audioDatabase: AudioDatabase): AudioDao = audioDatabase.audioDao()
}