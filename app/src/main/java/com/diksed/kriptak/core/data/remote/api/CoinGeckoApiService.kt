package com.diksed.kriptak.core.data.remote.api

import com.diksed.kriptak.core.data.model.CoinGeckoTrendingCoinsResponse
import retrofit2.http.GET

interface CoinGeckoApiService {
    @GET("search/trending")
    suspend fun getTrendingCoins(): CoinGeckoTrendingCoinsResponse

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}
