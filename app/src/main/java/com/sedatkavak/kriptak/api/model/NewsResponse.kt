package com.sedatkavak.kriptak.api.model

data class NewsResponse
    (
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)