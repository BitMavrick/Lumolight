package com.bitmavrick.lumolight.ui.tab.flashlight

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FlashlightViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FlashlightUiState())
    val uiState : StateFlow<FlashlightUiState> = _uiState

    fun updateFlashlightDuration(index : Int, time : Int){
        _uiState.update {
            it.copy(
                flashlightDurationIndex = index,
                flashlightDurationMin = time
            )
        }
    }

    fun updateFlashlightBpm(index : Int, value : Int){
        _uiState.update {
            it.copy(
                flashlightBpmIndex = index,
                flashlightBpmValue = value
            )
        }
    }

    fun updateFlashlightIntensity(index: Int, value : String){
        _uiState.update {
            it.copy(
                flashlightIntensityIndex = index,
                flashlightIntensityValue = value
            )
        }
    }

    fun updateFlashlightAlert(value : Boolean){
        _uiState.update {
            it.copy(
                flashlightAlertDialog = value
            )
        }
    }
}