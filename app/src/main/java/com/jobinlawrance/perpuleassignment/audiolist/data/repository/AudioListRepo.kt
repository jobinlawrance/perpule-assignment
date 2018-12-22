package com.jobinlawrance.perpuleassignment.audiolist.data.repository

import com.jobinlawrance.perpuleassignment.audiolist.AudioListContract
import javax.inject.Inject

class AudioListRepo @Inject constructor(private val audioListNetworkRepo: AudioListNetworkRepo):
    AudioListContract.Repository by audioListNetworkRepo