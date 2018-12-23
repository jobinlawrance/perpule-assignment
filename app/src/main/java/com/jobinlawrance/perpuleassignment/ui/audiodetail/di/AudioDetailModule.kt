package com.jobinlawrance.perpuleassignment.ui.audiodetail.di

import com.jobinlawrance.perpuleassignment.ui.audiodetail.AudioDetailContract
import com.jobinlawrance.perpuleassignment.ui.audiodetail.AudioDetailPresenter
import com.jobinlawrance.perpuleassignment.ui.audiodetail.data.AudioApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class AudioDetailModule {

    @Binds
    abstract fun providePresenter(audioDetailPresenter: AudioDetailPresenter): AudioDetailContract.Presenter

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideAudioListApi(retrofit: Retrofit): AudioApi = retrofit.create(AudioApi::class.java)
    }
}