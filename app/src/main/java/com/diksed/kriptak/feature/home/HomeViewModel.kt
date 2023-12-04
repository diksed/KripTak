package com.diksed.kriptak.feature.home

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.arch.BaseViewModel
import com.diksed.kriptak.arch.IViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeUiState>() {
    override fun createInitialState() = HomeUiState()

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
        }
    }


}

data class HomeUiState(
    val isLoading: Boolean = false,
    val title: String = "Home",
) : IViewState
