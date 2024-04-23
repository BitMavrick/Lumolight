package com.bitmavrick.lumolight.ui.tab.quickAction

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class QuickActionViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(QuickActionsUiState())
    val uiState : StateFlow<QuickActionsUiState> = _uiState

    fun updateSegmentedButtonIndex(value : Int) {
        _uiState.update {
            it.copy(
                segmentedButtonSelectedIndex = value
            )
        }
    }
}