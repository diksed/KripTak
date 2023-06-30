package com.sedatkavak.kriptak.screens.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.google.firebase.firestore.FirebaseFirestore
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.adapter.NewsAdapter
import com.sedatkavak.kriptak.adapter.TopMarketAdapter
import com.sedatkavak.kriptak.api.model.CryptoCurrency
import com.sedatkavak.kriptak.api.service.CoinGeckoApiService
import com.sedatkavak.kriptak.api.service.CoinGeckoApiUtilities
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiService
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiUtilities
import com.sedatkavak.kriptak.api.service.NewsApiService
import com.sedatkavak.kriptak.api.service.NewsApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentHomeBinding
import com.sedatkavak.kriptak.utils.CustomLinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewPager = activity?.findViewById(R.id.viewPager)!!
        getTrendingCoins()
        fetchApiKeyFromFirestore()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llAllCrypto.setOnClickListener {
            viewPager.currentItem = 2
        }
        binding.llAllNews.setOnClickListener {
            viewPager.currentItem = 3
        }
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
                }
            }
            .addOnFailureListener { exception ->
            }
    }
    private fun getNews(apiKey: String?, searchQuery : String?, language : String?) {
        binding.newsLoadingProgressBar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val res = NewsApiUtilities.getInstance().create(NewsApiService::class.java)
                .getNews(apiKey = apiKey!!, searchQuery = searchQuery!!, language = language!!)
            withContext(Dispatchers.Main) {
                binding.newsLoadingProgressBar.visibility = View.GONE
                binding.dailyNewsRecyclerView.adapter = NewsAdapter(res.body()!!.articles)
                val layoutManager = CustomLinearLayoutManager(requireContext())
                binding.dailyNewsRecyclerView.layoutManager = layoutManager
                binding.dailyNewsRecyclerView.isNestedScrollingEnabled = false
            }
        }
    }
    private fun getTrendingCoins() {
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
                            TopMarketAdapter(requireContext(), filteredCoins)
                        val layoutManager = CustomLinearLayoutManager(requireContext())
                        binding.topCurrencyRecyclerView.layoutManager = layoutManager
                        binding.topCurrencyRecyclerView.isNestedScrollingEnabled = false
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.dailyCryptoLoadingProgressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Kripto detayları alınamadı.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }
}
