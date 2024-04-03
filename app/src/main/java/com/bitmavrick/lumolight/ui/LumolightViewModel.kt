package com.bitmavrick.lumolight.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bitmavrick.lumolight.ui.utils.NavigationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LumolightViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LumolightUiState())
    val uiState: StateFlow<LumolightUiState> = _uiState

    fun updateCurrentNavigationItem(navigationItem: NavigationItem){
        Log.d("Tab", "The value is updating")
        _uiState.update {
            it.copy(
                currentNavigationItem = navigationItem
            )
        }
    }
}