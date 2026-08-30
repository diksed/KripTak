package com.diksed.kriptak

import android.app.Application
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.diksed.kriptak.data.worker.PriceAlertWorker
import com.diksed.kriptak.utils.Currency
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Stable
@HiltAndroidApp
class KripTakApp : Application(), Configuration.Provider {
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

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        schedulePriceAlertChecks()
    }

    private fun schedulePriceAlertChecks() {
        val request = PeriodicWorkRequestBuilder<PriceAlertWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            PRICE_ALERT_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    companion object {
        private const val PRICE_ALERT_WORK_NAME = "price_alert_check"
    }
}
