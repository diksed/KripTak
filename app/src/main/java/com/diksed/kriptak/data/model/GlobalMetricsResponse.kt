package com.diksed.kriptak.data.model

import com.google.gson.annotations.SerializedName

data class GlobalMetricsResponse(
    @SerializedName("data") val data: GlobalMetricsData
)

data class GlobalMetricsData(
    @SerializedName("active_cryptocurrencies") val activeCryptocurrencies: Int,
    @SerializedName("quote") val quote: Map<String, GlobalMetricsQuote>
)

data class GlobalMetricsQuote(
    @SerializedName("total_market_cap") val totalMarketCap: Double
)
