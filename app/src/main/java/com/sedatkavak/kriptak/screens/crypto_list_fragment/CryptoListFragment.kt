package com.sedatkavak.kriptak.screens.crypto_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sedatkavak.kriptak.R
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

        setupClickListeners()

        val lifecycleScope = viewLifecycleOwner.lifecycleScope
        cryptoUpdater = CryptoUpdater(binding, lifecycleScope, requireContext())
    }

    override fun onResume() {
        super.onResume()
        cryptoUpdater.fetchData()
    }

    private fun setupClickListeners() {
        binding.sortByNameButton.setOnClickListener {
            cryptoUpdater.sortByCrypto("name")
            binding.sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                if (cryptoUpdater.sortAscending) R.drawable.baseline_arrow_drop_down_24 else R.drawable.baseline_arrow_drop_up_24,
                0
            )
            binding.sortByPriceButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_24, 0)
            binding.sortByChangeButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_24, 0)
        }
        binding.sortByPriceButton.setOnClickListener{
            cryptoUpdater.sortByCrypto("price")
            binding.sortByPriceButton.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, if (cryptoUpdater.sortAscending) R.drawable.baseline_arrow_drop_down_24 else R.drawable.baseline_arrow_drop_up_24, 0)
            binding.sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_24, 0)
            binding.sortByChangeButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_24, 0)
        }
        binding.sortByChangeButton.setOnClickListener{
            cryptoUpdater.sortByCrypto("change")
            binding.sortByChangeButton.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, if (cryptoUpdater.sortAscending) R.drawable.baseline_arrow_drop_down_24 else R.drawable.baseline_arrow_drop_up_24, 0)
            binding.sortByNameButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_24, 0)
            binding.sortByPriceButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_24, 0)
        }
    }
}