package com.bitmavrick.lumolight.ui.tab.quickAction

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class QuickActionViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(QuickActionUiState())
    val uiState : StateFlow<QuickActionUiState> = _uiState

    private fun activateSegmentedButton(){
        _uiState.update {
            it.copy(
                segmentedButtonDisable = false
            )
        }
    }

    private fun deactivateSegmentedButton(){
        _uiState.update {
            it.copy(
                segmentedButtonDisable = true
            )
        }
    }

    fun updateSegmentedButtonIndex(value : Int) {
        _uiState.update {
            it.copy(
                segmentedButtonSelectedIndex = value
            )
        }
    }

    fun activeStartButton(){
        _uiState.update {
            it.copy(
                startButtonStatus = true
            )
        }
        deactivateSegmentedButton()
    }

    fun stopStartButton(){
        _uiState.update {
            it.copy(
                startButtonStatus = false
            )
        }
        activateSegmentedButton()
    }
}