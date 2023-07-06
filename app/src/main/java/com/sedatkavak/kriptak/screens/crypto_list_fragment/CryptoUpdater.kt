package com.sedatkavak.kriptak.screens.crypto_list_fragment

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedatkavak.kriptak.R
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
        binding.sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_24, 0)
        binding.sortByPriceButton.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.baseline_remove_24, 0)
        binding.sortByChangeButton.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.baseline_remove_24, 0)
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
                        sortCryptoList()
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

    private fun sortCryptoList(sortType : String = ""){
        if (sortByClicked){
            when(sortType){
                "name" -> {
                    val collator = Collator.getInstance(Locale("tr", "TR"))
                    coinList.sortWith(compareBy(collator) { it.name })
                    if (!sortAscending) {
                        coinList.reverse()
                    }
                }
                "price" -> {
                    coinList.sortBy { it.quotes[0].price }
                    if (!sortAscending) {
                        coinList.reverse()
                    }
                }
                "change" -> {
                    coinList.sortBy { it.quotes[0].percentChange24h }
                    if (!sortAscending) {
                        coinList.reverse()
                    }
                }
            }
        }
    }

    fun sortByCrypto(sortType: String = "name"){
        sortByClicked = true
        sortAscending = !sortAscending
        if (sortType == "name"){
            sortCryptoList("name")
    }
        else if (sortType == "price"){
            sortCryptoList("price")
        }
        else if (sortType == "change"){
            sortCryptoList("change")
        }
        binding.coinRecyclerView.scrollToPosition(0)
        binding.coinRecyclerView.adapter?.notifyDataSetChanged()
    }
}
