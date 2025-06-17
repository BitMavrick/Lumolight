/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.flashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.util.BpmValue
import com.bitmavrick.lumolight.util.TimeDuration
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FlashlightViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FlashlightUiState())
    val uiState : StateFlow<FlashlightUiState> = _uiState


    fun onEvent(event: FlashlightUiEvent){
        when(event) {
            is FlashlightUiEvent.UpdateFlashlightDuration -> {
                updateFlashlightDuration(event.index)
            }
            is FlashlightUiEvent.UpdateFlashlightBPM -> {
                updateFlashlightBpm(event.index)
            }

            is FlashlightUiEvent.ToggleFlashlight -> {
                toggleFlashLight(event.value)
            }

            is FlashlightUiEvent.UpdateMaxFlashlightStrengthIndex -> {
                updateMaxFlashlightStrengthIndex(event.value)
            }

            is FlashlightUiEvent.UpdateFlashlightStrength -> {
               updateFlashlightStrength(event.index)
            }
            is FlashlightUiEvent.UpdateFlashlightStatus -> {
                updateFlashlightStatus(event.status)
            }
        }
    }


    private fun updateFlashlightDuration(index : Int){
        _uiState.update {
            it.copy(
                flashlightDurationIndex = index,
            )
        }
    }

    private fun updateFlashlightBpm(index : Int){
        _uiState.update {
            it.copy(
                flashlightBpmIndex = index,
            )
        }
    }

    private fun updateMaxFlashlightStrengthIndex(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val strength = getMaxTorchStrengthValue(context)
            _uiState.update {
                it.copy(
                    flashlightMaxStrengthIndex = strength
                )
            }
        }
    }

    private fun updateFlashlightStrength(value: Int){
        _uiState.update {
            it.copy(
                flashlightStrength = value
            )
        }
    }


    private fun updateFlashlightStatus(value : Boolean){
        _uiState.update {
            it.copy(
                flashlightStatus = value
            )
        }
    }

    fun toggleFlashLight(context: Context) {
        val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        viewModelScope.launch {
            _uiState.collect { uiStateValue ->
                val flashlightBpmValue = BpmValue.list[uiStateValue.flashlightBpmIndex].value
                try {
                    if (!uiStateValue.flashlightStatus) {
                        cameraManager.setTorchMode(cameraId, false)
                    } else {
                        if (flashlightBpmValue == 0) {
                            if (Build.VERSION.SDK_INT >= 33 && uiStateValue.flashlightMaxStrengthIndex > 1) {
                                cameraManager.turnOnTorchWithStrengthLevel(cameraId, uiStateValue.flashlightStrength)
                            } else {
                                cameraManager.setTorchMode(cameraId, true)
                            }
                        } else {
                            val bpm = 60000L / flashlightBpmValue.toLong()
                            while (isActive && uiState.value.flashlightStatus) {
                                cameraManager.setTorchMode(cameraId, true)
                                delay(bpm / 2)
                                cameraManager.setTorchMode(cameraId, false)
                                delay(bpm / 2)
                            }
                        }
                    }
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun getMaxTorchStrengthValue(context: Context) : Int {
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)

    val torchMaxLevel = cameraCharacteristics[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL] ?: 1

    return torchMaxLevel
}