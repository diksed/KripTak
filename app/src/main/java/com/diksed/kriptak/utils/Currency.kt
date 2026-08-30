package com.diksed.kriptak.utils

import androidx.compose.runtime.staticCompositionLocalOf
import com.diksed.kriptak.KripTakApp

enum class Currency(val symbol: String) {
    USD("$"),
    TRY("₺")
}

/**
 * Gives price-formatting composables (see utils/formatters) access to the app-wide
 * selected currency and the cached USD -> TRY rate without threading them through
 * every intermediate composable's parameters. Provided once in MainActivity.
 */
val LocalKripTakApp = staticCompositionLocalOf<KripTakApp?> { null }
