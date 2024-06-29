package com.diksed.kriptak.data.remote.api

import com.diksed.kriptak.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getDailyNews(
        @Query("apiKey") apiKey: String,
        @Query("q") searchQuery: String,
        @Query("pageSize") pageSize: Int,
        @Query("excludeDomains") excludeDomains: String,
    ): NewsResponse
}
