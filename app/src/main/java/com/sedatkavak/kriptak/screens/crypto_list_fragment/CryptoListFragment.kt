package com.sedatkavak.kriptak.screens.crypto_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sedatkavak.kriptak.databinding.FragmentCryptoListBinding

class CryptoListFragment : Fragment() {
    private lateinit var binding: FragmentCryptoListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptoListBinding.inflate(inflater, container, false)
        return binding.root
    }

}