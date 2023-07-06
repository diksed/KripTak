package com.sedatkavak.kriptak.screens.crypto_list_fragment

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedatkavak.kriptak.adapter.CryptoAdapter
import com.sedatkavak.kriptak.api.model.CryptoCurrency
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiService
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentCryptoListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.text.Collator
import java.util.Locale

class CryptoUpdater(
    private val binding: FragmentCryptoListBinding,
    private val lifecycleScope: CoroutineScope,
    private val context: Context
) {

    private val coinList = mutableListOf<CryptoCurrency>()
    var sortAscending = false
    private var sortByClicked = false

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
                val response = CoinMarketCapApiUtilities.getInstance()
                    .create(CoinMarketCapApiService::class.java)
                    .getMarketData()

                if (response.isSuccessful) {
                    val coins = response.body()?.data?.cryptoCurrencyList

                    coins?.let { fetchedCoins ->
                        coinList.clear()
                        coinList.addAll(fetchedCoins)
                        sortCoinList()

                        withContext(Dispatchers.Main) {
                            binding.coinRecyclerView.adapter = CryptoAdapter(context, coinList)
                            val layoutManager = LinearLayoutManager(context)
                            binding.coinRecyclerView.layoutManager = layoutManager
                            binding.coinRecyclerView.scrollToPosition(0)
                        }
                    }
                } else {
                }
            } catch (e: IOException) {
            } catch (e: HttpException) {
            }
        }
    }

    private fun sortCoinList() {
        if (sortByClicked) {
            val collator = Collator.getInstance(Locale("tr", "TR"))
            coinList.sortWith(compareBy(collator) { it.name })
            if (!sortAscending) {
                coinList.reverse()
            }
        }
    }


    fun sortByCryptoName() {
        sortByClicked = true
        sortAscending = !sortAscending
        sortCoinList()
        binding.coinRecyclerView.scrollToPosition(0)
        binding.coinRecyclerView.adapter?.notifyDataSetChanged()
    }
}
