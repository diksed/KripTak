package com.diksed.kriptak.domain.viewstate.crypto

import androidx.compose.runtime.Stable
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class CryptoViewState(
    val isDark: Boolean = false,
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val isError: Boolean = false
) : IViewState