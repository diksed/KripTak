package com.diksed.kriptak.domain.viewstate.favorites

import androidx.compose.runtime.Stable
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class FavoritesViewState(
    val isDark: Boolean = false,
) : IViewState