package com.diksed.kriptak.features.screen.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.domain.repository.ApiParams
import com.diksed.kriptak.domain.repository.FirestoreRepository
import com.diksed.kriptak.domain.usecase.news.NewsUseCase
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.home.HomeViewState
import com.diksed.kriptak.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyNewsUseCase: NewsUseCase,
    private val firestoreRepository: FirestoreRepository,
    private val application: KripTakApp
) : BaseViewModel<HomeViewState, HomeViewEvent>() {

    init {
        setState { currentState.copy(isDark = application.isDark.value) }
        fetchApiParamsAndNews()
    }

    private fun fetchApiParamsAndNews() {
        viewModelScope.launch {
            try {
                val params = firestoreRepository.getDailyNewsApiParams()
                println("Params: $params")
                getDailyNews(params)
            } catch (e: Exception) {
                println("Error 1: ${e.message}")
            }
        }
    }

    private suspend fun getDailyNews(params: ApiParams) {
        try {
            val response = getDailyNewsUseCase(params)
            Log.d("NewsViewModel", "Fetched Articles: ${response.articles}")
            setState { currentState.copy(dailyNews = response.articles) }
        } catch (e: Exception) {
            Log.e("NewsViewModel", "Error fetching news: ${e.message}")
        }
    }

    private fun onChangeTheme() {
        viewModelScope.launch {
            application.toggleTheme()
            setState { currentState.copy(isDark = application.isDark.value) }
        }
    }

    override fun onTriggerEvent(event: HomeViewEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeViewEvent.OnChangeTheme -> onChangeTheme()
            }
        }
    }

    override fun createInitialState() = HomeViewState()
}

sealed class HomeViewEvent : IViewEvent {
    object OnChangeTheme : HomeViewEvent()
}
