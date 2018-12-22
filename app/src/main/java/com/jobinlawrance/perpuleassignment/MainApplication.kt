package com.jobinlawrance.perpuleassignment

import android.app.Activity
import android.app.Application
import com.jobinlawrance.perpuleassignment.di.ApplicationComponent
import com.jobinlawrance.perpuleassignment.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject



class MainApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationContext(applicationContext)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}