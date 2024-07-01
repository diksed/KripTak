package com.diksed.kriptak.data.remote.api

import com.diksed.kriptak.data.model.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface CoinService {
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCoins(
        @Header("X-CMC_PRO_API_KEY") apiKey: String
    ): CoinResponse
}