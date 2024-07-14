package com.diksed.kriptak.domain.usecase.news

import com.diksed.kriptak.data.model.NewsResponse
import com.diksed.kriptak.domain.repository.ApiParams
import com.diksed.kriptak.domain.repository.NewsRepository
import javax.inject.Inject

class DailyTurkishEnglishNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        params: ApiParams
    ): NewsResponse {
        return newsRepository.getDailyTurkishEnglishNews(params)
    }
}