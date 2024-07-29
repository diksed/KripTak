package com.diksed.kriptak.features.screen.favorites

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.usecase.coin.CoinFromSymbolUseCase
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.favorites.FavoritesViewState
import com.diksed.kriptak.features.base.BaseViewModel
import com.diksed.kriptak.utils.PreferencesManager
import com.diksed.kriptak.utils.convertToCoinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val application: KripTakApp,
    private val getCoinsFromSymbolUseCase: CoinFromSymbolUseCase,
    private val firestoreRepository: FirestoreRepository,
    private val preferencesManager: PreferencesManager
) : BaseViewModel<FavoritesViewState, FavoritesViewEvent>() {

    private val favoriteCoinsCache = mutableListOf<Coin>()

    init {
        setState { currentState.copy(isDark = application.isDark.value) }
        observeFavorites()
    }

    private fun fetchFavoriteCoins(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                if (favoriteCoinsCache.isEmpty() || forceRefresh) {
                    setState { currentState.copy(isLoading = true) }
                    val favoriteSymbols = preferencesManager.getFavorites()
                    setState { currentState.copy(favoriteCount = favoriteSymbols.size) }
                    val apiKey = firestoreRepository.getCoinMarketApiKey().coinMarketCapKey
                    val favoriteCoins = mutableListOf<Coin>()

                    for (symbol in favoriteSymbols) {
                        val response = getCoinsFromSymbolUseCase.invoke(apiKey, symbol)
                        val convertedCoins = convertToCoinResponse(response)
                        favoriteCoins.addAll(convertedCoins.data)
                    }

                    favoriteCoinsCache.clear()
                    favoriteCoinsCache.addAll(favoriteCoins)
                    setState { currentState.copy(favorites = favoriteCoinsCache, isLoading = false) }
                } else {
                    setState { currentState.copy(favorites = favoriteCoinsCache) }
                }
            } catch (e: Exception) {
                // TODO: Handle error
                setState { currentState.copy(isLoading = false) }
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            preferencesManager.favoritesFlow.collect { favoriteSymbols ->
                val currentSymbols = favoriteCoinsCache.map { it.symbol }
                if (currentSymbols != favoriteSymbols) {
                    fetchFavoriteCoins(forceRefresh = true)
                }
            }
        }
    }

    private fun onChangeTheme() {
        viewModelScope.launch {
            application.toggleTheme()
            setState { currentState.copy(isDark = application.isDark.value) }
        }
    }

    override fun onTriggerEvent(event: FavoritesViewEvent) {
        viewModelScope.launch {
            when (event) {
                is FavoritesViewEvent.OnChangeTheme -> onChangeTheme()
            }
        }
    }

    override fun createInitialState() = FavoritesViewState()
}


sealed class FavoritesViewEvent : IViewEvent {
    object OnChangeTheme : FavoritesViewEvent()
}