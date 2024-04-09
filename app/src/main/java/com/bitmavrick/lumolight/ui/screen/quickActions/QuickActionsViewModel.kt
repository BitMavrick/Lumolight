package com.bitmavrick.lumolight.ui.screen.quickActions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class QuickActionsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuickActionsUiState())
    val uiState : StateFlow<QuickActionsUiState> = _uiState

    fun activeStartButton(){
        _uiState.update {
            it.copy(
                startButtonStatus = true
            )
        }
    }

    fun stopStartButton(){
        _uiState.update {
            it.copy(
                startButtonStatus = false
            )
        }
    }

    fun updateSegmentedButtonStatus(value : Int){
        _uiState.update {
            it.copy(
                segmentedButtonIndex = value
            )
        }
    }
}