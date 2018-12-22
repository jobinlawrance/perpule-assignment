package com.jobinlawrance.perpuleassignment.ui.audiolist.data

import com.jobinlawrance.perpuleassignment.ApiResponse
import com.jobinlawrance.perpuleassignment.ui.audiolist.entities.AudioData
import io.reactivex.Observable
import retrofit2.http.GET

interface AudioListApi {

    @GET("https://api.myjson.com/bins/mxcsl")
    fun getAudioList(): Observable<ApiResponse<List<AudioData>>>
}