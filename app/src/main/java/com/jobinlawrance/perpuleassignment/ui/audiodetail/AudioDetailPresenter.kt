package com.jobinlawrance.perpuleassignment.ui.audiodetail

import android.annotation.SuppressLint
import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioServiceInterator
import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioViewState
import io.reactivex.Observable
import javax.inject.Inject

class AudioDetailPresenter @Inject constructor(
    val audioServiceInterator: AudioServiceInterator
) : AudioDetailContract.Presenter {

    private val downloadServiceObservable =
        audioServiceInterator.getService()
            .replay()
            .refCount()

    override fun playAudio(itemId: String, playNext: Boolean): Observable<AudioViewState> {
        return downloadServiceObservable
            .flatMap { it.playAudio(itemId, playNext) }
    }

    @SuppressLint("CheckResult")
    override fun stopAudio() {
        downloadServiceObservable.subscribe { it.stopSelf() }
    }
}