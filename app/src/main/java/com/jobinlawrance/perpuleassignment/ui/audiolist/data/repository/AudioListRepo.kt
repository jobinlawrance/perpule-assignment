package com.jobinlawrance.perpuleassignment.ui.audiolist.data.repository

import com.jobinlawrance.perpuleassignment.ui.audiolist.AudioListContract
import javax.inject.Inject

class AudioListRepo @Inject constructor(private val audioListNetworkRepo: AudioListNetworkRepo):
    AudioListContract.Repository by audioListNetworkRepo