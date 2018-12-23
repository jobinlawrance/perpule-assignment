package com.jobinlawrance.perpuleassignment.ui.audiodetail.ui

import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData

sealed class AudioViewState {
    class ShowNext(val audioData: AudioData): AudioViewState()
    class AudioState(val isLoading: Boolean): AudioViewState()
    object LaunchWelcome: AudioViewState()
    class Error(val message: String): AudioViewState()
}