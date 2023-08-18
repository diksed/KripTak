package com.sedatkavak.kriptak.screens.crypto_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
            sortAndUpdateUI(
                "name",
                binding.sortByNameButton,
                binding.sortByPriceButton,
                binding.sortByChangeButton
            )
        }
        binding.sortByPriceButton.setOnClickListener {
            sortAndUpdateUI(
                "price",
                binding.sortByPriceButton,
                binding.sortByNameButton,
                binding.sortByChangeButton
            )
        }
        binding.sortByChangeButton.setOnClickListener {
            sortAndUpdateUI(
                "change",
                binding.sortByChangeButton,
                binding.sortByNameButton,
                binding.sortByPriceButton
            )
        }
    }

    private fun sortAndUpdateUI(
        sortBy: String,
        activeButton: Button,
        inactiveButton1: Button,
        inactiveButton2: Button
    ) {
        cryptoUpdater.sortByCrypto(sortBy)
        activeButton.setCompoundDrawablesWithIntrinsicBounds(
            0, 0,
            if (cryptoUpdater.sortAscending)
                R.drawable.baseline_arrow_drop_down_24
            else
                R.drawable.baseline_arrow_drop_up_24,
            0
        )
        inactiveButton1.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.baseline_remove_24,
            0
        )
        inactiveButton2.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.baseline_remove_24,
            0
        )
    }
}
