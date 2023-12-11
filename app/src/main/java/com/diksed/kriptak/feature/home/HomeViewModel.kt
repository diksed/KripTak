package com.diksed.kriptak.feature.home

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.arch.BaseViewModel
import com.diksed.kriptak.arch.IViewState
import com.diksed.kriptak.core.data.model.Coin
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
        }
    }

    private suspend fun loadTrendingCoins() {
        try {
            val trendingCoinsResponse = coinGeckoRemoteDataSource.getTrendingCoins()
            val trendingCoins = trendingCoinsResponse.coins.map { coin ->
                Coin(
                    id = coin.item.id,
                    name = coin.item.name ?: "",
                    symbol = coin.item.symbol ?: "",
                    image = coin.item.large ?: "",
                    rank = coin.item.marketCapRank ?: 0
                )
            }

            saveTrendingCoinIds(trendingCoins.map { it.id })

            setState {
                copy(isLoading = false, trendingCoins = trendingCoins)
            }
        } catch (e: Exception) {
            setState {
                copy(isLoading = false, error = "Error loading trending coins")
            }
        }
    }

    private suspend fun saveTrendingCoinIds(coinIds: List<String>) {
        coinIdRepository.saveCoinIds(coinIds)
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val title: String = "Home",
    val trendingCoins: List<Coin> = emptyList(),
    val error: String? = null
) : IViewState
