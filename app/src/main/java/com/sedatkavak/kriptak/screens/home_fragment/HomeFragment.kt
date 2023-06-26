package com.sedatkavak.kriptak.screens.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sedatkavak.kriptak.api.adapter.TopMarketAdapter
import com.sedatkavak.kriptak.api.service.ApiService
import com.sedatkavak.kriptak.api.service.ApiUtilities
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
        getTopCurrencyList()
        return binding.root
    }
    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiService::class.java).getMarketData()

            withContext(Dispatchers.Main) {
                binding.topCurrencyRecyclerView.adapter = TopMarketAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
                val layoutManager = CustomLinearLayoutManager(requireContext())
                binding.topCurrencyRecyclerView.layoutManager = layoutManager
                binding.topCurrencyRecyclerView.isNestedScrollingEnabled = false
            }
        }
    }

}