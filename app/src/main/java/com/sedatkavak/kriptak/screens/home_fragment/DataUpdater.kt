package com.sedatkavak.kriptak.screens.home_fragment

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.adapter.NewsAdapter
import com.sedatkavak.kriptak.adapter.TopMarketAdapter
import com.sedatkavak.kriptak.api.service.CoinGeckoApiService
import com.sedatkavak.kriptak.api.service.CoinGeckoApiUtilities
import com.sedatkavak.kriptak.api.service.NewsApiService
import com.sedatkavak.kriptak.api.service.NewsApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentHomeBinding
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
        fetchApiKeyFromFirestore()
        getTrendingCoins()
    }

    private fun fetchApiKeyFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("NewsApi").document("apiKey")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val apiKey = document.getString("key")
                    val searchQuery = document.getString("searchQuery")
                    val language = document.getString("language")
                    getNews(apiKey, searchQuery, language)
                } else {
                    // Belirli bir durum için işlemler yapılabilir
                }
            }
            .addOnFailureListener { exception ->
                // Hata durumu için işlemler yapılabilir
            }
    }

    private fun getNews(apiKey: String?, searchQuery: String?, language: String?) {
        binding.newsLoadingProgressBar.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.loading_animation))
        binding.newsLoadingProgressBar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val res = NewsApiUtilities.getInstance().create(NewsApiService::class.java)
                .getNews(apiKey = apiKey!!, searchQuery = searchQuery!!, language = language!!)
            withContext(Dispatchers.Main) {
                binding.newsLoadingProgressBar.visibility = View.GONE
                res.body()?.articles?.let { articles ->
                    binding.dailyNewsRecyclerView.adapter = NewsAdapter(articles,3)
                    val layoutManager = LinearLayoutManager(context)
                    binding.dailyNewsRecyclerView.layoutManager = layoutManager
                    binding.dailyNewsRecyclerView.isNestedScrollingEnabled = false
                }
            }
        }
    }

    private fun getTrendingCoins() {
        binding.dailyCryptoLoadingProgressBar.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.loading_animation))
        binding.dailyCryptoLoadingProgressBar.visibility = View.VISIBLE
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
                        binding.topCurrencyRecyclerView.adapter =
                            TopMarketAdapter(context, filteredCoins)
                        val layoutManager = LinearLayoutManager(context)
                        binding.topCurrencyRecyclerView.layoutManager = layoutManager
                        binding.topCurrencyRecyclerView.isNestedScrollingEnabled = false
                    }
                }
            } catch (e: IOException) {
                // IOException için işlemler yapılabilir
            } catch (e: HttpException) {
                // HttpException için işlemler yapılabilir
            }
        }
    }
}