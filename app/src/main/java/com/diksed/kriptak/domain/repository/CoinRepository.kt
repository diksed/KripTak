package com.diksed.kriptak.domain.repository

import com.diksed.kriptak.data.model.CoinListResponse
import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.data.remote.api.CoinService
import javax.inject.Inject

interface CoinRepository {
    suspend fun getCoins(apiKey: String, start: Int, limit: Int): CoinResponse
    suspend fun getCoinWithSymbol(apiKey: String, symbol: String): CoinListResponse
}

class CoinRepositoryImpl @Inject constructor(
    private val apiService: CoinService
) : CoinRepository {
    override suspend fun getCoins(apiKey: String, start: Int, limit: Int): CoinResponse {
        return apiService.getCoins(apiKey = apiKey, start = start, limit = limit)
    }
    override suspend fun getCoinWithSymbol(apiKey: String, symbol: String): CoinListResponse {
        return apiService.getCoinWithSymbol(
            apiKey = apiKey,
            symbol = symbol
        )
    }
}
