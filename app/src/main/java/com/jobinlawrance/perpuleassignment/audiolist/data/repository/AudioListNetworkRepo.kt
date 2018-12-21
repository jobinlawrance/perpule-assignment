package com.jobinlawrance.perpuleassignment.audiolist.data.repository

import com.jobinlawrance.perpuleassignment.ApiResponse
import com.jobinlawrance.perpuleassignment.audiolist.AudioListContract
import com.jobinlawrance.perpuleassignment.audiolist.data.AudioListApi
import com.jobinlawrance.perpuleassignment.audiolist.entities.AudioData
import io.reactivex.Observable

class AudioListNetworkRepo(private val audioListApi: AudioListApi) : AudioListContract.Repository {

    override fun getAudioList(): Observable<ApiResponse<List<AudioData>>> = audioListApi.getAudioList()

}