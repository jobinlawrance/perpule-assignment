package com.jobinlawrance.perpuleassignment.ui.audiodetail.ui

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import com.jobinlawrance.perpuleassignment.persistance.Audio
import com.jobinlawrance.perpuleassignment.persistance.AudioDao
import com.jobinlawrance.perpuleassignment.ui.audiodetail.AudioDetailContract
import com.jobinlawrance.perpuleassignment.ui.audiodetail.data.AudioApi
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import okio.buffer
import okio.sink
import java.io.File
import java.net.URLDecoder
import javax.inject.Inject

class AudioService : Service(), AudioDetailContract.Service, MediaPlayer.OnPreparedListener {

    private val binder = AudioServiceBinder()

    @Inject
    lateinit var audioApi: AudioApi
    @Inject
    lateinit var audioDao: AudioDao

    private var isPrefetching = false
    private var prefetchItemId = ""

    private val prefetchSubject = BehaviorSubject.create<String>()
    private val stateSubject = PublishSubject.create<AudioViewState>()

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
    override fun playAudio(itemId: String, playNext: Boolean): Observable<AudioViewState> {
        mediaPlayer.reset()
        stateSubject.onNext(AudioViewState.AudioState(true))
        getAudio(itemId, playNext)
            .subscribe({
                mediaPlayer.run {
                    setDataSource(it)
                    prepareAsync()
                }
            }, {
                Log.e("###Perpule",it.message,it)
                stateSubject.onNext(AudioViewState.LaunchWelcome)
            })
        return stateSubject
    }

    override fun onPrepared(mp: MediaPlayer) {
        mp.start()
        stateSubject.onNext(AudioViewState.AudioState(false))
    }

    override fun getAudio(itemId: String, playNext: Boolean): Observable<String> {
        return getAudioById(itemId, playNext)
            .doOnNext {
                if (playNext)
                    stateSubject.onNext(AudioViewState.ShowNext(AudioData(it.audioUrl,it.desc,it.id)))
            }
            .flatMap {
                if (isPrefetching && prefetchItemId == it.id)
                    prefetchSubject
                else if (it.shouldDownload())
                    download(it.audioUrl, itemId)
                else
                    Observable.just(it.audioPath)
            }
            .doAfterNext {
                prefetch(itemId)
            }
            .applySchedulers()
    }

    fun download(url: String, itemId: String): Observable<String> {
        return audioApi
            .downloadAudio(url)
            .map { responseBody ->
                val fileName = URLDecoder.decode(url.substringAfterLast("/"), "UTF-8")
                val file = File(this.filesDir, fileName)
                val bufferedSink = file.sink().buffer()
                bufferedSink.write(responseBody.source(), responseBody.contentLength()).close()
                Log.d("###Perpule", "File path is ${file.absolutePath}")
                file.absolutePath
            }
            .doOnNext { path -> audioDao.setAudioPath(itemId, path) }
    }

    fun getAudioById(id: String, next: Boolean): Observable<Audio> {
        return if (next)
            audioDao.getNextAudio(id).toObservable()
        else
            audioDao.getAudioById(id).toObservable()
    }

    @SuppressLint("CheckResult")
    fun prefetch(itemId: String) {
        audioDao
            .getNextAudio(itemId)
            .filter { it.shouldDownload() }
            .toObservable()
            .flatMap {
                isPrefetching = true
                prefetchItemId = itemId
                download(it.audioUrl, it.id)
            }
            .doOnNext {
                isPrefetching = false
            }
            .subscribe(prefetchSubject::onNext) {
                Log.d("###Perpule", "Prefetch Error ${it.message}", it)
            }
    }

    inner class AudioServiceBinder : Binder() {
        val service: AudioService
            get() = this@AudioService
    }
}