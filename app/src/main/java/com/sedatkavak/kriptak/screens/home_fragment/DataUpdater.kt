package com.sedatkavak.kriptak.screens.home_fragment

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedatkavak.kriptak.adapter.CryptoAdapter
import com.sedatkavak.kriptak.api.service.CoinGeckoApiService
import com.sedatkavak.kriptak.api.service.CoinGeckoApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentHomeBinding
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionActivity
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionUtils
import com.sedatkavak.kriptak.screens.news_fragment.NewsDataUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class DataUpdater(
    private val binding: FragmentHomeBinding,
    private val lifecycleScope: CoroutineScope,
    private val context: Context
) {
    private val newsDataUpdater =
        NewsDataUpdater(context = context, lifecycleScope = lifecycleScope)
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
        if (!ConnectionUtils.isNetworkAvailable(context)) {
            val intent = Intent(context, ConnectionActivity::class.java)
            context.startActivity(intent)
            return
        }
        newsDataUpdater.fetchApiKeyFromFirestore(
            document = "apiDailyKey",
            progressBar = binding.newsLoadingProgressBar,
            binding.dailyNewsLoadingFrameLayout,
            recyclerView = binding.dailyNewsRecyclerView,
        )
        getTrendingCoins()
    }

    private fun getTrendingCoins() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response =
                    CoinGeckoApiUtilities.getInstance().create(CoinGeckoApiService::class.java)
                        .getTrendingCoins()
                val coins = response.body()?.coins
                coins?.let { trendingCoins ->
                    val coinSymbols = trendingCoins.map { it.item.symbol }
                    val filteredCoins = MatchCoinsSymbol().getFilteredCoins(coinSymbols)
                    withContext(Dispatchers.Main) {
                        binding.dailyCryptoLoadingProgressBar.visibility = View.GONE
                        binding.dailyCryptoLoadingFrameLayout.visibility = View.GONE
                        binding.topCurrencyRecyclerView.adapter =
                            CryptoAdapter(context, filteredCoins)
                        val layoutManager = LinearLayoutManager(context)
                        binding.topCurrencyRecyclerView.layoutManager = layoutManager
                        binding.topCurrencyRecyclerView.isNestedScrollingEnabled = false
                    }
                }
            } catch (e: IOException) {
                // To do

            } catch (e: HttpException) {
                // To do
            }
        }
    }
}
