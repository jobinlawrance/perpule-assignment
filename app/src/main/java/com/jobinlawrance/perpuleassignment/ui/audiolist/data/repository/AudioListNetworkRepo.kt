package com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository

import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.ui.audiolist.data.AudioListApi
import io.reactivex.Observable
import javax.inject.Inject

class AudioListNetworkRepo @Inject constructor(private val audioListApi: AudioListApi) : AudioListContract.Repository {

    override fun getAudioList(): Observable<AudioListPartialChange> =
        audioListApi
            .getAudioList()
            .map { AudioListPartialChange.AudioList(it.data) }

}