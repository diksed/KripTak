package com.diksed.kriptak.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CoinResponse(
    @SerializedName("status") val status: Status,
    @SerializedName("data") val data: List<Coin>
)

data class Status(
    @SerializedName("timestamp") val timestamp: Date,
    @SerializedName("error_code") val errorCode: Int,
    @SerializedName("error_message") val errorMessage: String?,
    @SerializedName("elapsed") val elapsed: Int,
    @SerializedName("credit_count") val creditCount: Int
)

data class Coin(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("num_market_pairs") val numMarketPairs: Int,
    @SerializedName("date_added") val dateAdded: Date,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("max_supply") val maxSupply: Long?,
    @SerializedName("circulating_supply") val circulatingSupply: Double,
    @SerializedName("total_supply") val totalSupply: Double,
    @SerializedName("platform") val platform: Platform?,
    @SerializedName("cmc_rank") val cmcRank: Int,
    @SerializedName("last_updated") val lastUpdated: Date,
    @SerializedName("quote") val quote: Quote
)

data class Platform(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("token_address") val tokenAddress: String
)

data class Quote(
    @SerializedName("USD") val usd: CurrencyInfo
)

data class CurrencyInfo(
    @SerializedName("price") val price: Double,
    @SerializedName("volume_24h") val volume24h: Double,
    @SerializedName("percent_change_1h") val percentChange1h: Double,
    @SerializedName("percent_change_24h") val percentChange24h: Double,
    @SerializedName("percent_change_7d") val percentChange7d: Double,
    @SerializedName("market_cap") val marketCap: Double,
    @SerializedName("last_updated") val lastUpdated: Date
)
