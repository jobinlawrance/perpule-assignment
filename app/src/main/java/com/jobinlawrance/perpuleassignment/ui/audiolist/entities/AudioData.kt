package com.jobinlawrance.perpuleassignment.ui.audiolist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AudioData(
    val audio: String,
    val desc: String,
    val itemId: String
) : Parcelable