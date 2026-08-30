package com.diksed.kriptak.data.remote.api

import com.diksed.kriptak.data.model.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateService {
    @GET("latest")
    suspend fun getRate(
        @Query("from") from: String = "USD",
        @Query("to") to: String = "TRY"
    ): ExchangeRateResponse
}
