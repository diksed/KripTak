package com.sedatkavak.kriptak.api.service

import com.sedatkavak.kriptak.api.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("http://newsapi.org/v2/everything?sortBy=publishedAt&language=tr&pageSize=3")
    suspend fun getNews(
        @Query("q")
        searchQuery: String = "Crypto",
        @Query("apiKey")
        apiKey: String = "49352c7e876d4ab688b7a7ed874e2adc"
    ): Response<NewsResponse>
}