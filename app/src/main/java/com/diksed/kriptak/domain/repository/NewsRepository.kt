package com.diksed.kriptak.domain.repository

import com.diksed.kriptak.data.model.NewsResponse
import com.diksed.kriptak.data.remote.api.NewsApiService
import javax.inject.Inject

interface NewsRepository {
    suspend fun getDailyNews(
        params: ApiParams
    ): NewsResponse
}

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService
) : NewsRepository {
    override suspend fun getDailyNews(
        params: ApiParams
    ): NewsResponse {
        return apiService.getDailyNews(
            apiKey = params.apiKey,
            pageSize = params.newsSize,
            searchQuery = params.searchQuery,
            excludeDomains = params.unwantedSources.joinToString(
                separator = ","
            )
        )
    }
}
