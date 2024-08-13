package com.diksed.kriptak.features.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.utils.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    init {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { isConnected ->
                _isConnected.value = isConnected
            }
        }
    }
}
