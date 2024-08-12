package com.diksed.kriptak.domain.viewstate.news

import androidx.compose.runtime.Stable
import com.diksed.kriptak.data.model.Article
import com.diksed.kriptak.domain.viewstate.IViewState

@Stable
data class NewsViewState(
    val isDark: Boolean = false,
    val turkishNews: List<Article> = emptyList(),
    val englishNews: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = true
) : IViewState