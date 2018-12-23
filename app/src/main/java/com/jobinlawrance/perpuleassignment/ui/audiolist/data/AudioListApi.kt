package com.jobinlawrance.perpuleassignment.ui.audiolist.data

import com.jobinlawrance.perpuleassignment.ApiResponse
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import io.reactivex.Observable
import retrofit2.http.GET

interface AudioListApi {

    @GET("https://demo3975508.mockable.io/perpule")
    fun getAudioList(): Observable<ApiResponse<List<AudioData>>>
}