package com.jobinlawrance.perpuleassignment.ui.audiodetail.di

import com.jobinlawrance.perpuleassignment.ui.audiodetail.data.AudioApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object AudioDetailModule {

    @JvmStatic
    @Provides
    fun provideAudioListApi(retrofit: Retrofit): AudioApi = retrofit.create(AudioApi::class.java)

}