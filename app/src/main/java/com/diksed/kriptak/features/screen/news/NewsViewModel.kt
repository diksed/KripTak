package com.diksed.kriptak.features.screen.news

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.news.NewsViewState
import com.diksed.kriptak.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val application: KripTakApp
) : BaseViewModel<NewsViewState, NewsViewEvent>() {

    init {
        setState { currentState.copy(isDark = application.isDark.value) }
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