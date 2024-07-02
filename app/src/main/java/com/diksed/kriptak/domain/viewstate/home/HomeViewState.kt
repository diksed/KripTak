package com.diksed.kriptak.domain.viewstate.home

import androidx.compose.runtime.Stable
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class HomeViewState(
    val isDark: Boolean = false,
    val dailyNews: List<Article> = emptyList(),
    val dailyTrendingCoins: List<CoinResponse?> = emptyList(),
    val isLoading: Boolean = false
) : IViewState