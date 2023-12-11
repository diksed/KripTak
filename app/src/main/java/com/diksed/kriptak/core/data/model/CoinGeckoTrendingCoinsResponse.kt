package com.diksed.kriptak.core.data.model

import com.google.gson.annotations.SerializedName

data class CoinGeckoTrendingCoinsResponse(
    val coins: List<CoinGeckoCoin>
) {
    data class CoinGeckoCoin(
        val item: ItemDto
    )

    data class ItemDto(
        val id: String,
        @SerializedName("coin_id")
        val coinId: Int? = null,
        val name: String? = null,
        val symbol: String? = null,
        @SerializedName("market_cap_rank")
        val marketCapRank: Int? = null,
        val thumb: String? = null,
        val small: String? = null,
        val large: String? = null,
        val slug: String? = null,
        @SerializedName("price_btc")
        val priceBtc: Double? = null,
        val score: Int? = null
    )
}
