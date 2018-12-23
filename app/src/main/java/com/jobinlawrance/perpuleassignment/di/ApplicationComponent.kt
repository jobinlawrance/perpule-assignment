package com.jobinlawrance.perpuleassignment.di

import android.content.Context
import com.jobinlawrance.perpuleassignment.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton


@Singleton
@Component(
    modules = [NetworkModule::class,
        RoomModule::class,
        AndroidInjectionModule::class,
        ActivityBuilder::class,
        ServiceBuilder::class]
)
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: MainApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): ApplicationComponent
    }
}