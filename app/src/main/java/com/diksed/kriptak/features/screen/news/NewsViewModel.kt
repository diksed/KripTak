package com.diksed.kriptak.features.screen.news

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.domain.repository.ApiParams
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.usecase.news.DailyTurkishEnglishNewsUseCase
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.news.NewsViewState
import com.diksed.kriptak.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: DailyTurkishEnglishNewsUseCase,
    private val firestoreRepository: FirestoreRepository,
    private val application: KripTakApp
) : BaseViewModel<NewsViewState, NewsViewEvent>() {

    init {
        setState { currentState.copy(isDark = application.isDark.value) }
        fetchApiParamsAndNews()
    }

    private fun fetchApiParamsAndNews() {
        viewModelScope.launch {
            try {
                setState { currentState.copy(isLoading = true) }
                val turkishParams = firestoreRepository.getDailyNewsApiParams("apiTrKey")
                val englishParams = firestoreRepository.getDailyNewsApiParams("apiEnKey")
                getDailyNews(turkishParams, englishParams)
                setState { currentState.copy(isLoading = false) }
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }

    private suspend fun getDailyNews(turkishParams: ApiParams, englishParams: ApiParams) {
        try {
            val turkishResponse = getNewsUseCase(turkishParams)
            val englishResponse = getNewsUseCase(englishParams)
            setState {
                currentState.copy(
                    turkishNews = turkishResponse.articles,
                    englishNews = englishResponse.articles
                )
            }
        } catch (e: Exception) {
            // TODO: Handle error
        }
    }

    private fun onChangeTheme() {
        viewModelScope.launch {
            application.toggleTheme()
            setState { currentState.copy(isDark = application.isDark.value) }
        }
    }

    override fun onTriggerEvent(event: NewsViewEvent) {
        viewModelScope.launch {
            when (event) {
                is NewsViewEvent.OnChangeTheme -> onChangeTheme()
            }
        }
    }

    override fun createInitialState() = NewsViewState()
}

sealed class NewsViewEvent : IViewEvent {
    object OnChangeTheme : NewsViewEvent()
}