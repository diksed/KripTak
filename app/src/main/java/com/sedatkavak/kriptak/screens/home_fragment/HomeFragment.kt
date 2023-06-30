package com.sedatkavak.kriptak.screens.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var dataUpdater: DataUpdater

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewPager = requireActivity().findViewById(R.id.viewPager)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupClickListeners()

        val lifecycleScope = viewLifecycleOwner.lifecycleScope
        dataUpdater = DataUpdater(binding, lifecycleScope, requireContext())
    }

    override fun onResume() {
        super.onResume()
        dataUpdater.startUpdating()
        dataUpdater.fetchData()
    }

    override fun onPause() {
        super.onPause()
        dataUpdater.stopUpdating()
    }

    private fun setupViewPager() {
        // ViewPager konfigürasyonu
    }

    private fun setupClickListeners() {
        binding.llAllCrypto.setOnClickListener {
            viewPager.currentItem = 2
        }
        binding.llAllNews.setOnClickListener {
            viewPager.currentItem = 3
        }
    }
}
