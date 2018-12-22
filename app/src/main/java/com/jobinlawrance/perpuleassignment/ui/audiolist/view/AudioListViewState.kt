package com.jobinlawrance.perpuleassignment.ui.audiolist.view

import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData

sealed class AudioListViewState {
    object Loading: AudioListViewState()
    class Success(val audioDataList: List<AudioData>): AudioListViewState()
    class Error(val message: String?): AudioListViewState()
}