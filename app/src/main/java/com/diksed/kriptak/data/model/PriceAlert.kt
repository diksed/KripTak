package com.diksed.kriptak.data.model

data class PriceAlert(
    val coinId: Int,
    val coinSymbol: String,
    val coinName: String,
    val targetPrice: Double,
    // true: notify once the price rises to/above targetPrice; false: once it falls to/below it.
    val isAbove: Boolean
)
