package com.jobinlawrance.perpuleassignment.ui.audiodetail.ui

sealed class AudioViewState {
    object Loading: AudioViewState()
    object Playing: AudioViewState()
    class Error(message: String): AudioViewState()
}