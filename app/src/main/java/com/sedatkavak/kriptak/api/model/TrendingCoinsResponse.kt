package com.sedatkavak.kriptak.api.model

data class TrendingCoinsResponse(
    val coins: List<TrendingCoin>
)

data class TrendingCoin(
    val item: CoinItem
)

data class CoinItem(
    val symbol: String,
)
