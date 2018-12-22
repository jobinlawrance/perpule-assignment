package com.jobinlawrance.perpuleassignment.ui.audiolist

import com.jobinlawrance.perpuleassignment.ApiResponse
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import com.jobinlawrance.perpuleassignment.ui.audiolist.view.AudioListViewState
import io.reactivex.Observable

interface AudioListContract {
    interface Repository {
        fun getAudioList(): Observable<ApiResponse<List<AudioData>>>
    }

    interface Presenter {
        fun getAudioList(): Observable<AudioListViewState>
    }

    interface View {
        fun renderView(audioListViewState: AudioListViewState)
    }
}