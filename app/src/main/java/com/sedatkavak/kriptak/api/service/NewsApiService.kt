package com.sedatkavak.kriptak.api.service

import com.sedatkavak.kriptak.api.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("http://newsapi.org/v2/everything?sortBy=publishedAt&language=en&pageSize=3")
    suspend fun getNews(
        @Query("q")
        searchQuery: String = "Crypto",
        @Query("apiKey")
        apiKey: String
    ): Response<NewsResponse>
}