package com.jobinlawrance.perpuleassignment.audiolist.data.repository

import com.jobinlawrance.perpuleassignment.audiolist.AudioListContract

class AudioListRepo(private val audioListNetworkRepo: AudioListNetworkRepo):
    AudioListContract.Repository by audioListNetworkRepo