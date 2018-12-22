package com.jobinlawrance.perpuleassignment.di

import dagger.Module
import com.jobinlawrance.perpuleassignment.audiolist.di.AudioListModule
import com.jobinlawrance.perpuleassignment.audiolist.view.AudioListActivity
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [AudioListModule::class])
    abstract fun bindAudioListActivty(): AudioListActivity
}