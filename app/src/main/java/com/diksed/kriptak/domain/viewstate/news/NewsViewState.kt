package com.diksed.kriptak.domain.viewstate.news

import androidx.compose.runtime.Stable
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class NewsViewState(
    val isDark: Boolean = false,
) : IViewState