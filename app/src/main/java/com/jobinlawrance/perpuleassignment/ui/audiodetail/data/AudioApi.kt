package com.jobinlawrance.perpuleassignment.ui.audiodetail.data

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface AudioApi {

    @GET
    @Streaming
    fun downloadAudio(@Url url: String): Observable<ResponseBody>
}