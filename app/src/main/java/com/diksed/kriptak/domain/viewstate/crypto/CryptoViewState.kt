package com.diksed.kriptak.domain.viewstate.crypto

import androidx.compose.runtime.Stable
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class CryptoViewState(
    val isDark: Boolean = false,
) : IViewState