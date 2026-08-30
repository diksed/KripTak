package com.diksed.kriptak.core.data.remote.source

import com.diksed.kriptak.core.data.model.CoinGeckoTrendCoinResponse
import com.diksed.kriptak.core.data.model.CoinResponse
import com.diksed.kriptak.core.data.remote.api.CoinGeckoApiService
import javax.inject.Inject

class CoinGeckoTrendingCoinsRemoteDataSource @Inject constructor(private val apiService: CoinGeckoApiService) {
    suspend fun getTrendingCoins(): CoinGeckoTrendCoinResponse {
        return apiService.getTrendingCoins()
    }

    suspend fun getCoins(): List<CoinResponse> {
        return apiService.getCoins()
    }
}
