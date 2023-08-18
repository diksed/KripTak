package com.sedatkavak.kriptak.screens.home_fragment

import com.sedatkavak.kriptak.api.model.CryptoCurrency
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiService
import com.sedatkavak.kriptak.api.service.CoinMarketCapApiUtilities
import com.sedatkavak.kriptak.utils.FragmentPositions.MAX_COINS

class MatchCoinsSymbol {
    suspend fun getFilteredCoins(coinSymbols: List<String>): List<CryptoCurrency> {
        val response = CoinMarketCapApiUtilities.getInstance()
            .create(CoinMarketCapApiService::class.java).getMarketData()
        val coinList = response.body()?.data?.cryptoCurrencyList
        val filteredCoins = mutableListOf<CryptoCurrency>()
        var matchedCount = 0

        for (symbol in coinSymbols) {
            val matchingCoin = coinList?.find { coin -> coin.symbol == symbol }
            if (matchingCoin != null) {
                filteredCoins.add(matchingCoin)
                matchedCount++
            }
            if (matchedCount == MAX_COINS) {
                break
            }
        }

        if (filteredCoins.size < MAX_COINS) {
            for (index in filteredCoins.size until coinSymbols.size) {
                val symbol = coinSymbols[index]
                val matchingCoin = coinList?.find { coin -> coin.symbol == symbol }
                if (matchingCoin != null) {
                    filteredCoins.add(matchingCoin)
                    matchedCount++
                }
                if (matchedCount == MAX_COINS) {
                    break
                }
            }
        }

        return filteredCoins
    }
}
