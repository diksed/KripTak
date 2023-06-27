package com.sedatkavak.kriptak.api.service

import com.sedatkavak.kriptak.api.model.TrendingCoinsResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoinGeckoApiService {
    @GET("api/v3/search/trending")
    suspend fun getTrendingCoins(): Response<TrendingCoinsResponse>
}