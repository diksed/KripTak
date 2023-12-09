package com.diksed.kriptak.feature.crypto_list

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.arch.BaseViewModel
import com.diksed.kriptak.arch.IViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor() : BaseViewModel<CryptoListUiState>() {
    override fun createInitialState() = CryptoListUiState()

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
        }
    }
}


data class CryptoListUiState(
    val isLoading: Boolean = false,
    val title: String = "CryptoList",
) : IViewState