package com.jobinlawrance.perpuleassignment.ui.audiolist.view

import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData

sealed class AudioListViewState {
    object Loading: AudioListViewState()
    class Success(val audioDataList: List<AudioData>): AudioListViewState()
    class NetworkError(val message: String): AudioListViewState()
    class DatabaseError(val message: String): AudioListViewState()
}