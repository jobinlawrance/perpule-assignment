package com.jobinlawrance.perpuleassignment.audiolist

import android.annotation.SuppressLint
import com.jobinlawrance.perpuleassignment.audiolist.view.AudioListViewState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AudioListPresenter @Inject constructor(val audioListRepo: AudioListContract.Repository) :
    AudioListContract.Presenter {

    private val viewStateSubject = PublishSubject.create<AudioListViewState>()

    @SuppressLint("CheckResult")
    override fun getAudioList(): Observable<AudioListViewState> {

        audioListRepo
            .getAudioList()
            .subscribe(
                {
                    viewStateSubject.onNext(AudioListViewState.Success(it.data))
                    viewStateSubject.onComplete()
                },
                {
                    viewStateSubject.onNext(AudioListViewState.Error("SomeError occurred"))
                    viewStateSubject.onComplete()
                }
                )

        return viewStateSubject
    }

}