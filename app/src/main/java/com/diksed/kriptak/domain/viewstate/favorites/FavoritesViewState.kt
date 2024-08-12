package com.diksed.kriptak.domain.viewstate.favorites

import androidx.compose.runtime.Stable
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class FavoritesViewState(
    val isDark: Boolean = false,
    val isLoading: Boolean = false,
    val favorites: List<Coin?> = emptyList(),
    val favoriteCount: Int = 0,
    val isError: Boolean = false
) : IViewState