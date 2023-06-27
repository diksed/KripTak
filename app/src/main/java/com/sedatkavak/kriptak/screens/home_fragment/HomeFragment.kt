package com.sedatkavak.kriptak.screens.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sedatkavak.kriptak.adapter.TopMarketAdapter
import com.sedatkavak.kriptak.api.service.CoinGeckoApiService
import com.sedatkavak.kriptak.api.service.CoinGeckoApiUtilities
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiService
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentHomeBinding
import com.sedatkavak.kriptak.utils.CustomLinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        getTrendingCoins()
        return binding.root
    }
    private fun getTrendingCoins() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response =
                    CoinGeckoApiUtilities.getInstance().create(CoinGeckoApiService::class.java)
                        .getTrendingCoins()
                val coins = response.body()?.coins
                coins?.let { trendingCoins ->
                    val coinSymbols = trendingCoins.take(3).map { it.item.symbol }
                    getTrendCoinDetails(coinSymbols)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Kripto detayları alınamadı.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun getTrendCoinDetails(coinSymbols: List<String>) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val symbolList = coinSymbols.joinToString(",")
                val response = CoinMarketCapApiUtilities.getInstance()
                    .create(CoinMarketCapApiService::class.java).getMarketData()
                val coinList = response.body()?.data?.cryptoCurrencyList
                coinList?.let { coinData ->
                    val filteredCoins =
                        coinData.filter { coin -> coinSymbols.contains(coin.symbol) }
                    withContext(Dispatchers.Main) {
                        binding.topCurrencyRecyclerView.adapter =
                            TopMarketAdapter(requireContext(), filteredCoins)
                        val layoutManager = CustomLinearLayoutManager(requireContext())
                        binding.topCurrencyRecyclerView.layoutManager = layoutManager
                        binding.topCurrencyRecyclerView.isNestedScrollingEnabled = false
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Kripto detayları alınamadı.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }


}
