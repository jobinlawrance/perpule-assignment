package com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository

import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData

sealed class AudioListPartialChange {
    class AudioList(val list: List<AudioData>): AudioListPartialChange()
    object NetworkError: AudioListPartialChange()
    object DatabaseError: AudioListPartialChange()
}