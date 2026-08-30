package com.diksed.kriptak

import android.app.Application
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.diksed.kriptak.utils.Currency
import dagger.hilt.android.HiltAndroidApp

@Stable
@HiltAndroidApp
class KripTakApp : Application() {
    val isDark = mutableStateOf(false)
    fun toggleTheme() {
        isDark.value = !isDark.value
    }

    val currency = mutableStateOf(Currency.USD)

    // Cached USD -> TRY rate, refreshed on app start (see HomeViewModel). Null until
    // the first fetch succeeds; the currency toggle stays disabled until then so we
    // never show a stale/guessed conversion.
    val usdToTryRate = mutableStateOf<Double?>(null)

    fun toggleCurrency() {
        currency.value = if (currency.value == Currency.USD) Currency.TRY else Currency.USD
    }
}
