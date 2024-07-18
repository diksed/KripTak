package com.diksed.kriptak.features.screen.crypto_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.diksed.kriptak.KripTakApp
import com.diksed.kriptak.data.model.Coin
import com.diksed.kriptak.domain.viewstate.IViewEvent
import com.diksed.kriptak.domain.viewstate.crypto_details.CryptoDetailsViewState
import com.diksed.kriptak.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(
    private val application: KripTakApp,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CryptoDetailsViewState, CryptoDetailsViewEvent>() {

    init {
        savedStateHandle.get<String>("cryptoDetail")?.let {
            setState { currentState.copy(isLoading = false, selectedCoin = Coin.create(it)) }
        } ?: kotlin.run {
            setState { currentState.copy(isLoading = true) }
        }
    }

    private fun onChangeTheme() {
        viewModelScope.launch {
            application.toggleTheme()
            setState { currentState.copy(isDark = application.isDark.value) }
        }
    }

    override fun onTriggerEvent(event: CryptoDetailsViewEvent) {
        viewModelScope.launch {
            when (event) {
                is CryptoDetailsViewEvent.OnChangeTheme -> onChangeTheme()
            }
        }
    }

    override fun createInitialState() = CryptoDetailsViewState()
}

sealed class CryptoDetailsViewEvent : IViewEvent {
    object OnChangeTheme : CryptoDetailsViewEvent()
}
