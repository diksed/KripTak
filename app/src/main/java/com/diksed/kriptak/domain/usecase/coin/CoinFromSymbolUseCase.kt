package com.diksed.kriptak.domain.usecase.coin

import com.diksed.kriptak.domain.repository.CoinRepository
import javax.inject.Inject

class CoinFromSymbolUseCase @Inject constructor(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(apiKey: String, symbol: String) =
        coinRepository.getCoinWithSymbol(apiKey = apiKey, symbol = symbol)
}
