package com.jobinlawrance.perpuleassignment

import android.app.Activity
import android.app.Application
import android.app.Service
import com.jobinlawrance.perpuleassignment.di.ApplicationComponent
import com.jobinlawrance.perpuleassignment.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject



class MainApplication: Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingServiceInjector

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