package com.sedatkavak.kriptak.screens.crypto_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sedatkavak.kriptak.databinding.FragmentCryptoListBinding

class CryptoListFragment : Fragment() {
    private lateinit var binding: FragmentCryptoListBinding
    private lateinit var cryptoUpdater: CryptoUpdater

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lifecycleScope = viewLifecycleOwner.lifecycleScope
        cryptoUpdater = CryptoUpdater(binding, lifecycleScope, requireContext())
    }

    override fun onResume() {
        super.onResume()
        cryptoUpdater.startUpdating()
        cryptoUpdater.fetchData()
    }

    override fun onPause() {
        super.onPause()
        cryptoUpdater.stopUpdating()
    }


}