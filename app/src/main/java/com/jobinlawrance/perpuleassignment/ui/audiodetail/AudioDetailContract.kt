package com.jobinlawrance.perpuleassignment.ui.audiodetail

import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioViewState
import io.reactivex.Observable

interface AudioDetailContract {
    interface Service {
        fun playAudio(itemId: String, playNext: Boolean): Observable<AudioViewState>
        fun getAudio(itemId: String, playNext: Boolean): Observable<String>
    }

    interface Presenter {
        fun playAudio(itemId: String, playNext: Boolean): Observable<AudioViewState>
        fun stopAudio()
    }

    interface View {
        fun renderView(audioViewState: AudioViewState)
    }
}