package com.diksed.kriptak.domain.usecase.coin

import com.diksed.kriptak.domain.repository.TrendingCoinRepository
import javax.inject.Inject

class TrendingCoinUseCase @Inject constructor(private val trendingCoinRepository: TrendingCoinRepository) {
    suspend operator fun invoke() = trendingCoinRepository.getTrendingCoins()
}