package com.diksed.kriptak.features.screen.crypto

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.usecase.coin.CoinUseCase
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.crypto.CryptoViewState
import com.diksed.kriptak.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val getCoinsUseCase: CoinUseCase,
    private val firestoreRepository: FirestoreRepository,
    private val application: KripTakApp
) : BaseViewModel<CryptoViewState, CryptoViewEvent>() {

    private var currentStart = 1
    private val limit = 50

    init {
        setState { currentState.copy(isDark = application.isDark.value) }
        fetchApiParamsAndCoins()
    }

    private fun fetchApiParamsAndCoins() {
        viewModelScope.launch {
            try {
                setState { currentState.copy(isLoading = true) }
                val apiKey = firestoreRepository.getCoinMarketApiKey().coinMarketCapKey
                loadMoreCoins(apiKey)
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }

    private suspend fun loadMoreCoins(apiKey: String) {
        try {
            val response = getCoinsUseCase(apiKey = apiKey, start = currentStart, limit = limit)
            val newCoins = response.data
            setState { currentState.copy(coins = currentState.coins + newCoins, isLoading = false) }
            currentStart += limit
        } catch (e: Exception) {
            // TODO: Handle error
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            try {
                val apiKey = firestoreRepository.getCoinMarketApiKey().coinMarketCapKey
                loadMoreCoins(apiKey)
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }

    private fun onChangeTheme() {
        viewModelScope.launch {
            application.toggleTheme()
            setState { currentState.copy(isDark = application.isDark.value) }
        }
    }

    override fun onTriggerEvent(event: CryptoViewEvent) {
        viewModelScope.launch {
            when (event) {
                is CryptoViewEvent.OnChangeTheme -> onChangeTheme()
            }
        }
    }

    override fun createInitialState() = CryptoViewState()
}

sealed class CryptoViewEvent : IViewEvent {
    object OnChangeTheme : CryptoViewEvent()
}
