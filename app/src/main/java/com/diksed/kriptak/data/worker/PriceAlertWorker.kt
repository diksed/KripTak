package com.diksed.kriptak.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.usecase.coin.CoinFromSymbolUseCase
import com.diksed.kriptak.utils.NotificationHelper
import com.diksed.kriptak.utils.PreferencesManager
import javax.inject.Inject

/**
 * Periodically checks every saved price alert against the latest coin price
 * and fires a local notification (via the existing FCM notification channel)
 * for any that have been hit, then clears them so they don't repeat.
 *
 * Deliberately NOT using @HiltWorker/@AssistedInject here: that combination
 * hits a known kapt/Kotlin-metadata bug on CoroutineWorker subclasses
 * (https://github.com/google/dagger/issues/4693, still open as of Kotlin 2.1+).
 * PriceAlertWorkerFactory below wires this up to Hilt manually instead.
 */
class PriceAlertWorker(
    context: Context,
    params: WorkerParameters,
    private val preferencesManager: PreferencesManager,
    private val getCoinFromSymbolUseCase: CoinFromSymbolUseCase,
    private val firestoreRepository: FirestoreRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val alerts = preferencesManager.getPriceAlerts()
        if (alerts.isEmpty()) return Result.success()

        val apiKey = try {
            firestoreRepository.getCoinMarketApiKey().coinMarketCapKey
        } catch (e: Exception) {
            return Result.retry()
        }

        for (alert in alerts) {
            try {
                val response = getCoinFromSymbolUseCase(apiKey = apiKey, symbol = alert.coinSymbol)
                val price = response.data.values.firstOrNull()?.quote?.get("USD")?.price ?: continue
                val targetHit = if (alert.isAbove) {
                    price >= alert.targetPrice
                } else {
                    price <= alert.targetPrice
                }
                if (targetHit) {
                    val formattedPrice = "$${"%.2f".format(price)}"
                    NotificationHelper.showPriceAlert(applicationContext, alert, formattedPrice)
                    preferencesManager.removePriceAlert(alert.coinId)
                }
            } catch (e: Exception) {
                // Skip this coin this cycle - it'll be checked again on the next run.
            }
        }

        return Result.success()
    }
}

class PriceAlertWorkerFactory @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val getCoinFromSymbolUseCase: CoinFromSymbolUseCase,
    private val firestoreRepository: FirestoreRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            PriceAlertWorker::class.java.name -> PriceAlertWorker(
                appContext,
                workerParameters,
                preferencesManager,
                getCoinFromSymbolUseCase,
                firestoreRepository
            )
            else -> null
        }
    }
}
