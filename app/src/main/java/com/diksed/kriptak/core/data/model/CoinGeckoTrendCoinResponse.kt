package com.diksed.kriptak.core.data.model

data class CoinGeckoTrendCoinResponse(
    val coins: List<CoinGeckoTrendCoin>
) {
    data class CoinGeckoTrendCoin(
        val item: CoinItemDetails
    )

    data class CoinItemDetails(
        val id: String,
        val coin_id: Int,
        val name: String,
        val symbol: String,
        val market_cap_rank: Int,
        val thumb: String,
        val small: String,
        val large: String,
        val slug: String,
        val price_btc: Double,
        val score: Int,
        val data: CoinData,
    )

    data class CoinData(
        val price: String,
        val price_btc: String,
        val price_change_percentage_24h: PriceChangePercentage24h,
        val market_cap: String,
        val market_cap_btc: String,
        val total_volume: String,
        val total_volume_btc: String,
        val sparkline: String,
        val content: CoinContent,
    )

    data class PriceChangePercentage24h(
        val usd: Double,
        val eur: Double,
    )

    data class CoinContent(
        val title: String,
        val description: String
    )
}
