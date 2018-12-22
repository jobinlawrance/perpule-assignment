package com.jobinlawrance.perpuleassignment.di

import com.jobinlawrance.perpuleassignment.ui.audiodetail.di.AudioDetailModule
import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioDetailActivity
import dagger.Module
import com.jobinlawrance.perpuleassignment.ui.audiolist.di.AudioListModule
import com.jobinlawrance.perpuleassignment.ui.audiolist.view.AudioListActivity
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [AudioListModule::class])
    abstract fun bindAudioListActivty(): AudioListActivity

    @ContributesAndroidInjector(modules = [AudioDetailModule::class])
    abstract fun bindAudioDetailActivity(): AudioDetailActivity
}