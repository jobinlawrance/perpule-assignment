package com.jobinlawrance.perpuleassignment.di

import com.jobinlawrance.perpuleassignment.ui.audiodetail.di.AudioDetailModule
import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilder {

    @ContributesAndroidInjector(modules = [AudioDetailModule::class])
    abstract fun bindAudioService(): AudioService

}