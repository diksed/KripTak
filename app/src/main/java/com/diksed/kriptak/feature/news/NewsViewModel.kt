package com.diksed.kriptak.feature.news

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.arch.BaseViewModel
import com.diksed.kriptak.arch.IViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : BaseViewModel<NewsUiState>() {
    override fun createInitialState() = NewsUiState()

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
        }
    }
}

data class NewsUiState(
    val isLoading: Boolean = false,
    val title: String = "News",
) : IViewState
