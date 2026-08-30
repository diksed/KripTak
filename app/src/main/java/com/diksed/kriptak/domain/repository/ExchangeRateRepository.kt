package com.diksed.kriptak.domain.repository

import com.diksed.kriptak.data.remote.api.ExchangeRateService
import javax.inject.Inject

interface ExchangeRateRepository {
    suspend fun getUsdToTryRate(): Double
}

class ExchangeRateRepositoryImpl @Inject constructor(
    private val apiService: ExchangeRateService
) : ExchangeRateRepository {
    override suspend fun getUsdToTryRate(): Double {
        val response = apiService.getRate(from = "USD", to = "TRY")
        return response.rates["TRY"] ?: error("TRY rate missing from exchange rate response")
    }
}
