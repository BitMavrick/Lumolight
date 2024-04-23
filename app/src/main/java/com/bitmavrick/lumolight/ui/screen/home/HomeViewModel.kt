package com.bitmavrick.lumolight.ui.screen.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState

    fun updateTabIndex(value: Int){
        _uiState.update {
            it.copy(
                selectedTabIndex = value
            )
        }
    }
}