package com.diksed.kriptak.domain.usecase.coin

import com.diksed.kriptak.domain.repository.CoinRepository
import javax.inject.Inject

class CoinUseCase @Inject constructor(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(apiKey: String, start: Int, limit: Int) = coinRepository.getCoins(apiKey = apiKey, start = start, limit = limit)
}
