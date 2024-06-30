package com.diksed.kriptak.features.screen.home

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
                setState { currentState.copy(isLoading = true)}
                val params = firestoreRepository.getDailyNewsApiParams()
                getDailyNews(params)
                setState { currentState.copy(isLoading = false)}
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }

    private suspend fun getDailyNews(params: ApiParams) {
        try {
            val response = getDailyNewsUseCase(params)
            setState { currentState.copy(dailyNews = response.articles) }
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
