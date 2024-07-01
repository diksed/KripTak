package com.diksed.kriptak.domain.repository

import com.diksed.kriptak.data.model.TrendingCoinResponse
import com.diksed.kriptak.data.remote.api.TrendingCoinService
import javax.inject.Inject

interface TrendingCoinRepository {
    suspend fun getTrendingCoins(): TrendingCoinResponse
}

class TrendingCoinRepositoryImpl @Inject constructor(
    private val apiService: TrendingCoinService
) : TrendingCoinRepository {
    override suspend fun getTrendingCoins(): TrendingCoinResponse {
        return apiService.getTrendingCoins()
    }
}