package com.bitmavrick.lumolight.ui.screen.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState

    fun onEvent(event: HomeUiEvent){
        when(event){
            is HomeUiEvent.updateTab -> {
                updateTabIndex(event.tabIndex)
            }

            is HomeUiEvent.updateShowAboutDialog -> {
                updateShowAboutDialog(event.status)
            }
        }
    }

    private fun updateTabIndex(value: Int){
        _uiState.update {
            it.copy(
                selectedTabIndex = value
            )
        }
    }

    private fun updateShowAboutDialog(status : Boolean){
        _uiState.update {
            it.copy(
                showAboutDialog = status
            )
        }
    }
}