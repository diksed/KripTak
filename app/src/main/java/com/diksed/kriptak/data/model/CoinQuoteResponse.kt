package com.diksed.kriptak.data.model

import com.google.gson.annotations.SerializedName

data class CoinListResponse(
    @SerializedName("data") val coins: List<CoinQuoteResponse>
)

data class CoinQuoteResponse(
    @SerializedName("data") val data: Map<String, CoinQuoteItem>
)

data class CoinQuoteItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("cmc_rank") val cmcRank: Int,
    @SerializedName("quote") val quote: Map<String, SymbolQuote>
)

data class SymbolQuote(
    @SerializedName("price") val price: Double,
    @SerializedName("volume_24h") val volume24h: Double,
    @SerializedName("percent_change_1h") val percentChange1h: Double,
    @SerializedName("percent_change_24h") val percentChange24h: Double,
    @SerializedName("percent_change_7d") val percentChange7d: Double,
    @SerializedName("market_cap") val marketCap: Double
)
