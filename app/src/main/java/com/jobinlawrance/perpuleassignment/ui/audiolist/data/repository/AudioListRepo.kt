package com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository

import android.annotation.SuppressLint
import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AudioListRepo @Inject constructor(
    private val audioListNetworkRepo: AudioListNetworkRepo,
    private val audioListLocalRepo: AudioListLocalRepo
) : AudioListContract.Repository {

    private val audioListSubject =
        PublishSubject.create<AudioListPartialChange>()

    val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getAudioList(): Observable<AudioListPartialChange> {
        //network call
        compositeDisposable +=
                audioListNetworkRepo
                    .getAudioList()
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        if (it is AudioListPartialChange.AudioList)
                            audioListLocalRepo.updateLocalRepo(it.list)
                    }) {
                        audioListSubject.onNext(AudioListPartialChange.NetworkError)
                    }

        //local data observable
        compositeDisposable += audioListLocalRepo
            .getAudioList()
            .subscribe(audioListSubject::onNext) {
                audioListSubject.onNext(AudioListPartialChange.DatabaseError)
            }

        return audioListSubject
            .doOnDispose {
                compositeDisposable.dispose()
            }
    }

}
