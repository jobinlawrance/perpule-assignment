package com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository

import com.jobinlawrance.perpuleassignment.ApiResponse
import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.ui.audiolist.data.AudioListApi
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import io.reactivex.Observable
import javax.inject.Inject

class AudioListNetworkRepo @Inject constructor(private val audioListApi: AudioListApi) : AudioListContract.Repository {

    override fun getAudioList(): Observable<List<AudioData>> = audioListApi.getAudioList().map { it.data }

}