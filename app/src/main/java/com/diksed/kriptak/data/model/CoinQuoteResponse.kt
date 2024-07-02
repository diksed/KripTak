package com.diksed.kriptak.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CoinListResponse(
    @SerializedName("status") val status: ResponseStatus,
    @SerializedName("data") val data: Map<String, CoinData>
)

data class CoinData(
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
    @SerializedName("quote") val quote: Map<String, CurrencyInfo>
)

data class ResponseStatus(
    @SerializedName("timestamp") val timestamp: Date,
    @SerializedName("error_code") val errorCode: Int,
    @SerializedName("error_message") val errorMessage: String?,
    @SerializedName("elapsed") val elapsed: Int,
    @SerializedName("credit_count") val creditCount: Int
)
