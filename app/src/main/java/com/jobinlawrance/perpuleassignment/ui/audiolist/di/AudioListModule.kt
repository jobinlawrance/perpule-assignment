package com.jobinlawrance.perpuleassignment.ui.audiolist.di

import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListPresenter
import com.jobinlawrance.perpuleassignment.ui.audiolist.data.AudioListApi
import com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository.AudioListRepo
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