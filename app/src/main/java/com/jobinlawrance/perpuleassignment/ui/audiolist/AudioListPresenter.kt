package com.jobinlawrance.perpuleassignment.ui.audiolist

import android.annotation.SuppressLint
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository.AudioListPartialChange
import com.jobinlawrance.perpuleassignment.ui.audiolist.view.AudioListViewState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AudioListPresenter @Inject constructor(val audioListRepo: AudioListContract.Repository) :
    AudioListContract.Presenter {

    private val viewStateSubject = PublishSubject.create<AudioListViewState>()
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getAudioList(): Observable<AudioListViewState> {

        compositeDisposable +=
                audioListRepo
                    .getAudioList()
                    .applySchedulers()
                    .map {
                         when (it) {
                            is AudioListPartialChange.AudioList -> {
                                AudioListViewState.Success(it.list)
                            }
                            AudioListPartialChange.NetworkError -> {
                                AudioListViewState.NetworkError("Network error, Try Again.")
                            }
                            AudioListPartialChange.DatabaseError -> {
                                AudioListViewState.DatabaseError("Ooops, there's some error")
                            }
                        }
                    }
                    .subscribe(
                        {
                            viewStateSubject.onNext(it)
                        },
                        {
                            viewStateSubject.onComplete()
                        }
                    )

        return viewStateSubject.doOnDispose {
            compositeDisposable.dispose()
        }
    }

}