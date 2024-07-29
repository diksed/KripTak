package com.diksed.kriptak.domain.viewstate.crypto_details

import androidx.compose.runtime.Stable
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class CryptoDetailsViewState(
    val isDark: Boolean = false,
    val isLoading: Boolean = false,
    val isCryptoLoading: Boolean = false,
    val cryptoNews: List<Article> = emptyList(),
    val selectedCoin: Coin? = null,
    val isFavorite: Boolean = false
) : IViewState