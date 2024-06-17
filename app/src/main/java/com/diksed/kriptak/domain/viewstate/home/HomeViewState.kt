package com.diksed.kriptak.domain.viewstate.home

import androidx.compose.runtime.Stable
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class HomeViewState(
    val isDark: Boolean = false,
) : IViewState