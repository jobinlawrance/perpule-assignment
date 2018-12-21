package com.jobinlawrance.perpuleassignment.audiolist

import com.jobinlawrance.perpuleassignment.ApiResponse
import com.jobinlawrance.perpuleassignment.audiolist.entities.AudioData
import io.reactivex.Observable

interface AudioListContract {
    interface Repository {
        fun getAudioList(): Observable<ApiResponse<List<AudioData>>>
    }
}