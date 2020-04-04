package com.aura.co99.networks

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api/")
    fun getImages(
        @Query("key") key: String,
        @Query("page") page: Int,
        @Query("q") keyword: String,
        @Query("safesearch") safesearch: Boolean
    ): Observable<ImagesResponses>
}