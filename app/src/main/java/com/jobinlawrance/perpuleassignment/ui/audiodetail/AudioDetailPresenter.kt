package com.jobinlawrance.perpuleassignment.ui.audiodetail

import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import com.jobinlawrance.perpuleassignment.persistance.AudioDao
import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioServiceInterator
import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioViewState
import io.reactivex.Observable
import javax.inject.Inject

class AudioDetailPresenter @Inject constructor(
    val audioServiceInterator: AudioServiceInterator,
    val audioDao: AudioDao
) : AudioDetailContract.Presenter {

    private val downloadServiceObservable by lazy {
        audioServiceInterator.getService()
            .replay()
            .refCount()
    }

    override fun playAudio(itemId: String, playNext: Boolean): Observable<AudioViewState> {
        return downloadServiceObservable
            .flatMap { it.playAudio(itemId, playNext) }
            .applySchedulers()
    }
}