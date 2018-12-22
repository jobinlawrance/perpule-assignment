package com.jobinlawrance.perpuleassignment.ui.audiodetail.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AudioServiceInterator @Inject constructor(private val context: Context) {

    fun getService(): Observable<AudioService> {

        val serviceSubject = PublishSubject.create<AudioService>()
        val serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                serviceSubject.onComplete()
            }

            override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
                val audioServiceBinder = iBinder as AudioService.AudioServiceBinder
                val audioService = audioServiceBinder.service
                if (serviceSubject.hasObservers())
                    serviceSubject.onNext(audioService)
            }

        }

        val serviceHandler = object : IServiceHandler {

            override fun bind(serviceConnection: ServiceConnection) {
                val intent = Intent(context.applicationContext, AudioService::class.java)
                context.startService(intent)
                context.bindService(intent, serviceConnection, 0)
            }

            override fun unBind(serviceConnection: ServiceConnection) {
                context.unbindService(serviceConnection)
            }

        }

        serviceHandler.bind(serviceConnection)

        return serviceSubject.doOnDispose {
            serviceHandler.unBind(serviceConnection)
        }
    }

    private interface IServiceHandler {
        fun bind(serviceConnection: ServiceConnection)
        fun unBind(serviceConnection: ServiceConnection)
    }
}