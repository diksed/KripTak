package com.sedatkavak.kriptak.screens.crypto_list_fragment

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.adapter.CryptoAdapter
import com.sedatkavak.kriptak.api.model.CryptoCurrency
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiService
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentCryptoListBinding
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionActivity
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionUtils
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
    private val filteredCoinList = mutableListOf<CryptoCurrency>()
    var sortAscending = false
    private var sortByClicked = false

    fun fetchData() {
        if (!ConnectionUtils.isNetworkAvailable(context)) {
            val intent = Intent(context, ConnectionActivity::class.java)
            context.startActivity(intent)
            return
        }
        getCoins()
        binding.sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.baseline_remove_24,
            0
        )
        binding.sortByPriceButton.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.baseline_remove_24,
            0
        )
        binding.sortByChangeButton.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.baseline_remove_24,
            0
        )
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
                        filterCryptoList(binding.coinSearchEditText.text.toString())
                        withContext(Dispatchers.Main) {
                            binding.coinRecyclerView.adapter =
                                CryptoAdapter(context, filteredCoinList)
                            val layoutManager = LinearLayoutManager(context)
                            binding.coinRecyclerView.layoutManager = layoutManager
                            binding.coinRecyclerView.scrollToPosition(0)
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Sunucudan veri alınamadı",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: IOException) {
                Log.e("Network Error", "IO Exception", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Ağ hatası", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: HttpException) {
                Log.e("Network Error", "HTTP Exception", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Ağ hatası", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sortCryptoList(sortType: String = "") {
        if (sortByClicked) {
            when (sortType) {
                "name" -> {
                    val collator = Collator.getInstance(Locale("tr", "TR"))
                    filteredCoinList.sortWith(compareBy(collator) { it.name })
                    if (!sortAscending) {
                        filteredCoinList.reverse()
                    }
                }

                "price" -> {
                    filteredCoinList.sortBy { it.quotes[0].price }
                    if (!sortAscending) {
                        filteredCoinList.reverse()
                    }
                }

                "change" -> {
                    filteredCoinList.sortBy { it.quotes[0].percentChange24h }
                    if (!sortAscending) {
                        filteredCoinList.reverse()
                    }
                }
            }
        }
    }

    fun sortByCrypto(sortType: String = "name") {
        sortByClicked = true
        sortAscending = !sortAscending
        sortCryptoList(sortType)
        binding.coinRecyclerView.scrollToPosition(0)
        val adapter = binding.coinRecyclerView.adapter
        adapter?.notifyItemRangeChanged(0, adapter.itemCount)
    }

    fun filterCryptoList(filter: String) {
        filteredCoinList.clear()
        if (filter.isEmpty()) {
            filteredCoinList.addAll(coinList)
        } else {
            val lowerCaseFilter = filter.lowercase(Locale.getDefault())
            for (coin in coinList) {
                if (coin.name.lowercase(Locale.getDefault()).contains(lowerCaseFilter)) {
                    filteredCoinList.add(coin)
                }
            }
        }
        binding.coinRecyclerView.adapter?.notifyDataSetChanged()
    }

    init {
        binding.coinSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                filterCryptoList(s.toString())
            }
        })
    }
}
