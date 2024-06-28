package com.diksed.kriptak.features.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    val uiState: StateFlow<SplashUiState> = mainUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SplashUiState.Loading
        )
}

private fun mainUiState(): Flow<SplashUiState> {
    return flow {
        emit(SplashUiState.Loading)
        delay(1000)
        emit(SplashUiState.Success)
    }
}

sealed interface SplashUiState {
    object Loading : SplashUiState
    object Success : SplashUiState
}