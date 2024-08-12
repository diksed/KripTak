package com.diksed.kriptak.features.screen.home

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.data.model.CoinResponse
import com.diksed.kriptak.domain.repository.ApiParams
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.usecase.coin.CoinFromSymbolUseCase
import com.diksed.kriptak.domain.usecase.coin.TrendingCoinUseCase
import com.diksed.kriptak.domain.usecase.news.NewsUseCase
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.home.HomeViewState
import com.diksed.kriptak.features.base.BaseViewModel
import com.diksed.kriptak.utils.convertToCoinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyNewsUseCase: NewsUseCase,
    private val getTrendingCoinsUseCase: TrendingCoinUseCase,
    private val getCoinsFromSymbolUseCase: CoinFromSymbolUseCase,
    private val firestoreRepository: FirestoreRepository,
    private val application: KripTakApp
) : BaseViewModel<HomeViewState, HomeViewEvent>() {

    init {
        setState { currentState.copy(isDark = application.isDark.value) }
        fetchApiParamsAndNews()
    }

    private fun fetchApiParamsAndNews() {
        viewModelScope.launch {
            var errorOccurred = false
            setState { currentState.copy(isLoading = true) }

            try {
                val params = firestoreRepository.getDailyNewsApiParams("apiDailyKey")
                try {
                    getDailyNews(params)
                } catch (e: Exception) {
                    errorOccurred = true
                }

                try {
                    getTrendingCoins()
                } catch (e: Exception) {
                    errorOccurred = true
                }

            } catch (e: Exception) {
                errorOccurred = true
            } finally {
                setState { currentState.copy(isLoading = false, isError = errorOccurred) }
            }
        }
    }

    private suspend fun getTrendingCoins() {
        try {
            val response = getTrendingCoinsUseCase()
            val apiKey = firestoreRepository.getCoinMarketApiKey()
            val trendingCoins = response.coins
            val coinDetails = mutableListOf<CoinResponse>()

            for (coinItem in trendingCoins) {
                if (coinDetails.size >= 3) break

                val symbol = coinItem.item.symbol ?: continue

                val coinResponse = try {
                    val coinListResponse = getCoinsFromSymbolUseCase(
                        apiKey = apiKey.coinMarketCapKey,
                        symbol = symbol
                    )
                    if (coinListResponse.data.isEmpty()) continue
                    convertToCoinResponse(coinListResponse)
                } catch (e: Exception) {
                    throw e
                }
                coinResponse.let { coinDetails.add(it) }
            }
            setState { currentState.copy(dailyTrendingCoins = coinDetails) }
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getDailyNews(params: ApiParams) {
        try {
            val response = getDailyNewsUseCase(params)
            setState { currentState.copy(dailyNews = response.articles) }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun onChangeTheme() {
        viewModelScope.launch {
            application.toggleTheme()
            setState { currentState.copy(isDark = application.isDark.value) }
        }
    }

    override fun onTriggerEvent(event: HomeViewEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeViewEvent.OnChangeTheme -> onChangeTheme()
            }
        }
    }

    override fun createInitialState() = HomeViewState()
}

sealed class HomeViewEvent : IViewEvent {
    object OnChangeTheme : HomeViewEvent()
}
