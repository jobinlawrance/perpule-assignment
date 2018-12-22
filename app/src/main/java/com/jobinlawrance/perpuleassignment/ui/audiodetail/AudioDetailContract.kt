package com.jobinlawrance.perpuleassignment.ui.audiodetail

import com.jobinlawrance.perpuleassignment.ui.audiodetail.ui.AudioViewState
import io.reactivex.Observable

interface AudioDetailContract {
    interface Service {
        fun playAudio(itemId: String): Observable<AudioViewState>
        fun downloadAudio(itemId: String): Observable<String>
    }
}