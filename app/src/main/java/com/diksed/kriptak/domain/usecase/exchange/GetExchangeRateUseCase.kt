package com.diksed.kriptak.domain.usecase.exchange

import com.diksed.kriptak.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {
    suspend operator fun invoke() = exchangeRateRepository.getUsdToTryRate()
}
