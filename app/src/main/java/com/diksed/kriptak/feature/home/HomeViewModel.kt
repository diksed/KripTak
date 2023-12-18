package com.diksed.kriptak.feature.home

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.arch.BaseViewModel
import com.diksed.kriptak.arch.IViewState
import com.diksed.kriptak.core.data.remote.source.CoinGeckoTrendingCoinsRemoteDataSource
import com.diksed.kriptak.core.data.repository.CoinIdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinGeckoRemoteDataSource: CoinGeckoTrendingCoinsRemoteDataSource,
    private val coinIdRepository: CoinIdRepository
) : BaseViewModel<HomeUiState>() {

    override fun createInitialState() = HomeUiState()

    init {
        viewModelScope.launch {
            loadTrendingCoins()
            loadCoins()
        }
    }

    private suspend fun loadTrendingCoins() {
        setState { copy(isLoading = true) }
        try {
            val trendingCoins =
                coinGeckoRemoteDataSource.getTrendingCoins().coins.map { it.item.name }
            setState { copy(trendingCoins = trendingCoins) }
        } catch (e: Exception) {
            setState { copy(error = e.message) }
        } finally {
            setState { copy(isLoading = false) }
        }
    }

    private suspend fun loadCoins() {
        setState { copy(isLoading = true) }
        try {
            val coins = coinGeckoRemoteDataSource.getCoins().map { it.name }
            setState { copy(coins = coins) }
        } catch (e: Exception) {
            setState { copy(error = e.message) }
        } finally {
            setState { copy(isLoading = false) }
        }
    }


    private suspend fun saveTrendingCoinIds(coinIds: List<String>) {
        coinIdRepository.saveCoinIds(coinIds)
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val title: String = "Home",
    val trendingCoins: List<String> = emptyList(),
    val coins: List<String> = emptyList(),
    val error: String? = null
) : IViewState
