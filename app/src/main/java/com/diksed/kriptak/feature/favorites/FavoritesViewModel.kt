package com.diksed.kriptak.feature.favorites

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.arch.BaseViewModel
import com.diksed.kriptak.arch.IViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : BaseViewModel<FavoritesUiState>() {
    override fun createInitialState() = FavoritesUiState()

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
        }
    }
}

data class FavoritesUiState(
    val isLoading: Boolean = false,
    val title: String = "Favorites",
) : IViewState
