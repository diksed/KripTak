package com.diksed.kriptak.features.screen.crypto

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.usecase.coin.CoinUseCase
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.crypto.CryptoViewState
import com.diksed.kriptak.features.base.BaseViewModel
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val getCoinsUseCase: CoinUseCase,
    private val firestoreRepository: FirestoreRepository,
    private val application: KripTakApp
) : BaseViewModel<CryptoViewState, CryptoViewEvent>() {

    private var currentStart = 1
    private val limit = 2499

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _sortType = MutableStateFlow(SortType.NONE)
    val sortType: StateFlow<SortType> get() = _sortType

    private val _sortDirection = MutableStateFlow(SortDirection.DEFAULT)
    val sortDirection: StateFlow<SortDirection> get() = _sortDirection

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
                setState { currentState.copy(isLoading = false, isError = true) }
            }
        }
    }

    private suspend fun loadMoreCoins(apiKey: String) {
        try {
            val response = getCoinsUseCase(apiKey = apiKey, start = currentStart, limit = limit)
            setState {
                currentState.copy(
                    coins = currentState.coins + response.data,
                    isLoading = false
                )
            }
            currentStart += limit
        } catch (e: Exception) {
            setState { currentState.copy(isLoading = false, isError = true) }
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            try {
                val apiKey = firestoreRepository.getCoinMarketApiKey().coinMarketCapKey
                loadMoreCoins(apiKey)
            } catch (e: Exception) {
                setState { currentState.copy(isError = true) }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSortType(sortType: SortType) {
        if (_sortType.value == sortType) {
            _sortDirection.value = when (_sortDirection.value) {
                SortDirection.ASCENDING -> SortDirection.DESCENDING
                SortDirection.DESCENDING -> SortDirection.ASCENDING
                else -> SortDirection.DEFAULT
            }
        } else {
            _sortDirection.value = SortDirection.ASCENDING
        }
        _sortType.value = sortType
        setState {
            currentState.copy(
                coins = sortCoins(
                    currentState.coins,
                    sortType,
                    _sortDirection.value
                )
            )
        }
    }

    private fun sortCoins(
        coins: List<Coin>,
        sortType: SortType,
        sortDirection: SortDirection
    ): List<Coin> {
        return when (sortType) {
            SortType.NAME -> {
                if (sortDirection == SortDirection.ASCENDING) {
                    coins.sortedBy { it.name }
                } else {
                    coins.sortedByDescending { it.name }
                }
            }

            SortType.PRICE -> {
                if (sortDirection == SortDirection.ASCENDING) {
                    coins.sortedBy { it.quote.usd.price }
                } else {
                    coins.sortedByDescending { it.quote.usd.price }
                }
            }

            SortType.PERCENTAGE -> {
                if (sortDirection == SortDirection.ASCENDING) {
                    coins.sortedBy { it.quote.usd.percentChange24h }
                } else {
                    coins.sortedByDescending { it.quote.usd.percentChange24h }
                }
            }

            else -> coins
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
