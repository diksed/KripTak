package com.sedatkavak.kriptak.screens.crypto_list_fragment

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedatkavak.kriptak.adapter.CryptoAdapter
import com.sedatkavak.kriptak.api.service.CoinGeckoApiService
import com.sedatkavak.kriptak.api.service.CoinGeckoApiUtilities
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiService
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentCryptoListBinding
import com.sedatkavak.kriptak.databinding.FragmentHomeBinding
import com.sedatkavak.kriptak.screens.home_fragment.MatchCoinsSymbol
import com.sedatkavak.kriptak.screens.news_fragment.NewsDataUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class CryptoUpdater(
    private val binding: FragmentCryptoListBinding,
    private val lifecycleScope: CoroutineScope,
    private val context: Context
) {

    private val updateInterval: Long = 30000
    private val updateHandler = android.os.Handler()
    private val updateRunnable = object : Runnable {
        override fun run() {
            fetchData()
            updateHandler.postDelayed(this, updateInterval)
        }
    }
    fun startUpdating() {
        updateHandler.postDelayed(updateRunnable, updateInterval)
    }
    fun stopUpdating() {
        updateHandler.removeCallbacks(updateRunnable)
    }
    fun fetchData() {
        getCoins()
    }
    private fun getCoins() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response =
                    CoinMarketCapApiUtilities.getInstance().create(CoinMarketCapApiService::class.java)
                        .getMarketData()



            } catch (e: IOException) {
                // IOException için işlemler yapılabilir
            } catch (e: HttpException) {
                // HttpException için işlemler yapılabilir
            }
        }
    }
}