package com.diksed.kriptak.features.screen.home.components.trending_coins

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

fun getPercentChangeColorFilter(percentChange24h: Double): ColorFilter? {
    return when {
        percentChange24h > 0 -> ColorFilter.tint(Color.Green)
        percentChange24h < 0 -> ColorFilter.tint(Color.Red)
        else -> null
    }
}
