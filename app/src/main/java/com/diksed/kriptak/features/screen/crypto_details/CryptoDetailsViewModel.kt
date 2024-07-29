package com.diksed.kriptak.features.screen.crypto_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.domain.repository.ApiParams
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.usecase.news.NewsUseCase
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.crypto_details.CryptoDetailsViewState
import com.diksed.kriptak.features.base.BaseViewModel
import com.diksed.kriptak.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(
    private val application: KripTakApp,
    private val getNewsUseCase: NewsUseCase,
    private val firestoreRepository: FirestoreRepository,
    private val preferencesManager: PreferencesManager,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CryptoDetailsViewState, CryptoDetailsViewEvent>() {

    init {
        savedStateHandle.get<String>("cryptoDetail")?.let { coinId ->
            val selectedCoin = Coin.create(coinId)
            setState { currentState.copy(isLoading = false, selectedCoin = selectedCoin) }
            if (selectedCoin != null) {
                checkFavoriteStatus(selectedCoin.symbol)
            }
        } ?: run {
            setState { currentState.copy(isLoading = true) }
        }
    }

    fun checkFavoriteStatus(coinSymbol: String) {
        viewModelScope.launch {
            val isFavorite = preferencesManager.isFavorite(coinSymbol)
            setState { currentState.copy(isFavorite = isFavorite) }
        }
    }

    fun toggleFavorite(coinSymbol: String) {
        viewModelScope.launch {
            val isFavorite = preferencesManager.isFavorite(coinSymbol)
            if (isFavorite) {
                preferencesManager.removeFavorite(coinSymbol)
            } else {
                preferencesManager.addFavorite(coinSymbol)
            }
            setState { currentState.copy(isFavorite = !isFavorite) }
        }
    }

    fun fetchNews(coinId: String) {
        viewModelScope.launch {
            setState { currentState.copy(isCryptoLoading = true) }
            try {
                val apiKey = firestoreRepository.getDailyNewsApiParams("coinNewsKey")
                val response = getNewsUseCase(
                    ApiParams(
                        apiKey = apiKey.apiKey,
                        language = "en",
                        newsSize = 3,
                        searchQuery = "+$coinId",
                        unwantedSources = emptyList()
                    )
                )

                val filteredArticles = response.articles
                    .filter { it.source.name != "[Removed]" }
                    .distinctBy { it.title }

                setState { currentState.copy(cryptoNews = filteredArticles) }
            } catch (e: Exception) {
                // TODO: Handle error
            } finally {
                setState { currentState.copy(isCryptoLoading = false) }
            }
        }
    }

    private fun onChangeTheme() {
        viewModelScope.launch {
            application.toggleTheme()
            setState { currentState.copy(isDark = application.isDark.value) }
        }
    }

    override fun onTriggerEvent(event: CryptoDetailsViewEvent) {
        viewModelScope.launch {
            when (event) {
                is CryptoDetailsViewEvent.OnChangeTheme -> onChangeTheme()
                is CryptoDetailsViewEvent.ToggleFavorite -> toggleFavorite(event.coinSymbol)
            }
        }
    }

    override fun createInitialState() = CryptoDetailsViewState()
}

sealed class CryptoDetailsViewEvent : IViewEvent {
    object OnChangeTheme : CryptoDetailsViewEvent()
    data class ToggleFavorite(val coinSymbol: String) : CryptoDetailsViewEvent()
}