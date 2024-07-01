package com.diksed.kriptak.domain.repository

import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.data.remote.api.CoinService
import javax.inject.Inject

interface CoinRepository {
    suspend fun getCoins(apiKey: String): CoinResponse
}

class CoinRepositoryImpl @Inject constructor(
    private val apiService: CoinService
) : CoinRepository {
    override suspend fun getCoins(apiKey: String): CoinResponse {
        return apiService.getCoins(
            apiKey = apiKey
        )
    }
}
