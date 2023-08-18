package com.sedatkavak.kriptak.screens.home_fragment

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.adapter.CryptoAdapter
import com.sedatkavak.kriptak.api.service.CoinGeckoApiService
import com.sedatkavak.kriptak.api.service.CoinGeckoApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentHomeBinding
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionActivity
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionUtils
import com.sedatkavak.kriptak.screens.news_fragment.NewsDataUpdater
import com.sedatkavak.kriptak.screens.news_fragment.UIComponents
import com.sedatkavak.kriptak.utils.SecondsConstants.UPDATE_INTERVAL
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
    private val updateInterval: Long = UPDATE_INTERVAL
    private val updateHandler = android.os.Handler(Looper.getMainLooper())
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
            uiComponents = UIComponents(
                binding.newsLoadingProgressBar,
                binding.dailyNewsLoadingFrameLayout,
                binding.dailyNewsRecyclerView,
            )
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
                Log.e("Network Error", "IO Exception", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.network_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } catch (e: HttpException) {
                Log.e("Network Error", "HTTP Exception", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_data),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
