package com.bitmavrick.lumolight.ui.tab.flashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    /*
    fun updateFlashlightIntensity(index: Int, value : String){
        _uiState.update {
            it.copy(
                flashlightIntensityIndex = index,
                flashlightIntensityValue = value
            )
        }
    }

     */

    fun updateFlashlightAlert(value : Boolean){
        _uiState.update {
            it.copy(
                flashlightStatus = value
            )
        }
    }


    fun toggleFlashLight(context: Context, status: Boolean) {
        val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        // val bpm = 60000L / uiState.value.flashlightBpmValue.toLong()

        viewModelScope.launch {
            if(uiState.value.flashlightBpmValue == 0){
                try {
                    cameraManager.setTorchMode(cameraId, status)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }else{
                while (uiState.value.flashlightStatus) {
                    try {
                        cameraManager.setTorchMode(cameraId, true)
                        delay(500)
                        cameraManager.setTorchMode(cameraId, false)
                        delay(500)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}