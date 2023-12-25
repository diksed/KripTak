package com.diksed.kriptak.core.data.remote.api

import com.diksed.kriptak.core.data.model.CoinGeckoTrendCoinResponse
import com.diksed.kriptak.core.data.model.CoinResponse
import retrofit2.http.GET

interface CoinGeckoApiService {
    @GET("search/trending")
    suspend fun getTrendingCoins(): CoinGeckoTrendCoinResponse

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=true&locale=en")
    suspend fun getCoins(): List<CoinResponse>

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}
