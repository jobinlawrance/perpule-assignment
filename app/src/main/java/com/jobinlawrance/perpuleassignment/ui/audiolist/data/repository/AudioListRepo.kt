package com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.jobinlawrance.perpuleassignment.extensions.applySchedulers
import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import io.reactivex.Observable
import javax.inject.Inject

class AudioListRepo @Inject constructor(
    private val audioListNetworkRepo: AudioListNetworkRepo,
    private val audioListLocalRepo: AudioListLocalRepo
) : AudioListContract.Repository {

    @SuppressLint("CheckResult")
    override fun getAudioList(): Observable<List<AudioData>> {
        //network call
        audioListNetworkRepo
            .getAudioList()
            .doOnNext(audioListLocalRepo::updateLocalRepo)
            .applySchedulers()
            .subscribe({}) {
                Log.e("###Perpule",it.message,it)
            }

        //local data observable
        return audioListLocalRepo
            .getAudioList()
    }

}