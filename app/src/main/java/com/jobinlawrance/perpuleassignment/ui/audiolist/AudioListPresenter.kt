package com.jobinlawrance.perpuleassignment.ui.audiolist

import android.annotation.SuppressLint
import com.jobinlawrance.perpuleassignment.ui.audiolist.view.AudioListViewState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AudioListPresenter @Inject constructor(val audioListRepo: AudioListContract.Repository) :
    AudioListContract.Presenter {

    private val viewStateSubject = PublishSubject.create<AudioListViewState>()

    @SuppressLint("CheckResult")
    override fun getAudioList(): Observable<AudioListViewState> {

        audioListRepo
            .getAudioList()
            .subscribeOn(Schedulers.io())
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