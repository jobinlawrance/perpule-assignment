package com.jobinlawrance.perpuleassignment.ui.audiodetail.ui

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import com.jobinlawrance.perpuleassignment.ui.audiodetail.AudioDetailContract
import com.jobinlawrance.perpuleassignment.ui.audiodetail.data.AudioApi
import dagger.android.AndroidInjection
import io.reactivex.Observable
import okio.buffer
import okio.sink
import java.io.File
import javax.inject.Inject

class AudioService : Service(), AudioDetailContract.Service, MediaPlayer.OnPreparedListener {

    private val binder = AudioServiceBinder()

    @Inject
    lateinit var audioApi: AudioApi

    private val mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener(this@AudioService)
        setOnCompletionListener { reset() }
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    @SuppressLint("CheckResult")
    override fun playAudio(itemId: String): Observable<AudioViewState> {
        downloadAudio("")
            .subscribe {
                mediaPlayer.run {
                    reset()
                    setDataSource(it)
                    prepareAsync()
                }
            }
        return Observable.just(AudioViewState.Playing)
    }

    override fun onPrepared(mp: MediaPlayer) {
        mp.start()
    }

    override fun downloadAudio(itemId: String): Observable<String> {
        return audioApi
            .downloadAudio("https://sample-videos.com/audio/mp3/crowd-cheering.mp3")
            .map {
                val fileName = "monsterslap.mp3"
                val file = File(this.filesDir, fileName)
                val bufferedSink = file.sink().buffer()
                bufferedSink.write(it.source(), it.contentLength()).close()
                Log.d("###Perpule", "File path is ${file.absolutePath}")
                file.absolutePath
            }
            .applySchedulers()
    }

    inner class AudioServiceBinder : Binder() {
        val service: AudioService
            get() = this@AudioService
    }
}