package com.jobinlawrance.perpuleassignment.audiolist.view

import com.jobinlawrance.perpuleassignment.audiolist.entities.AudioData

sealed class AudioListViewState {
    object Loading: AudioListViewState()
    class Success(val audioDataList: List<AudioData>): AudioListViewState()
    class Error(val message: String?): AudioListViewState()
}