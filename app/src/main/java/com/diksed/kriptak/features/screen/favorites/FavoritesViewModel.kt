package com.diksed.kriptak.features.screen.favorites

import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.favorites.FavoritesViewState
import com.diksed.kriptak.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val application: KripTakApp
) : BaseViewModel<FavoritesViewState, FavoritesViewEvent>() {

    init {
        setState { currentState.copy(isDark = application.isDark.value) }
    }

    private fun onChangeTheme() {
        viewModelScope.launch {
            application.toggleTheme()
            setState { currentState.copy(isDark = application.isDark.value) }
        }
    }

    override fun onTriggerEvent(event: FavoritesViewEvent) {
        viewModelScope.launch {
            when (event) {
                is FavoritesViewEvent.OnChangeTheme -> onChangeTheme()
            }
        }
    }

    override fun createInitialState() = FavoritesViewState()
}

sealed class FavoritesViewEvent : IViewEvent {
    object OnChangeTheme : FavoritesViewEvent()
}
