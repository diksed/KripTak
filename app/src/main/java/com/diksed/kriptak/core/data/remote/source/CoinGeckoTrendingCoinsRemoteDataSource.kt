package com.diksed.kriptak.core.data.remote.source

import com.diksed.kriptak.core.data.model.CoinGeckoTrendingCoinsResponse
import com.diksed.kriptak.core.data.remote.api.CoinGeckoApiService
import javax.inject.Inject

class CoinGeckoTrendingCoinsRemoteDataSource @Inject constructor(private val apiService: CoinGeckoApiService) {
    suspend fun getTrendingCoins(): CoinGeckoTrendingCoinsResponse {
        return apiService.getTrendingCoins()
    }
}
