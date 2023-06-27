package com.sedatkavak.kriptak.api.service

import com.sedatkavak.kriptak.api.model.MarketModel
import retrofit2.Response
import retrofit2.http.GET

interface CoinMarketCapApiService {
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getMarketData(): Response<MarketModel>
}
