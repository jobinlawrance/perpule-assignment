package com.jobinlawrance.perpuleassignment.audiolist.di

import com.jobinlawrance.perpuleassignment.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.audiolist.AudioListPresenter
import com.jobinlawrance.perpuleassignment.audiolist.data.AudioListApi
import com.jobinlawrance.perpuleassignment.audiolist.data.repository.AudioListRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class AudioListModule {

    @Binds
    abstract fun provideRepo(audioListRepo: AudioListRepo): AudioListContract.Repository

    @Binds
    abstract fun providePresenter(audioListPresenter: AudioListPresenter): AudioListContract.Presenter

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideAudioListApi(retrofit: Retrofit): AudioListApi = retrofit.create(AudioListApi::class.java)
    }
}