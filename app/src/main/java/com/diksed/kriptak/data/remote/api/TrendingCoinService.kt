package com.diksed.kriptak.data.remote.api

import com.diksed.kriptak.data.model.TrendingCoinResponse
import retrofit2.http.GET

interface TrendingCoinService {
    @GET("search/trending")
    suspend fun getTrendingCoins(): TrendingCoinResponse
}