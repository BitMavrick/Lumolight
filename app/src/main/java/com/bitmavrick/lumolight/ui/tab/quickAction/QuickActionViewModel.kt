/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuickActionViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(QuickActionUiState())
    val uiState : StateFlow<QuickActionUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.segmentedButtonValue,
                userPreferencesRepository.saveQuickAction,
                userPreferencesRepository.enableHapticStatus
            ) { segmentedButtonValue, shouldSaveSegmentedButtonIndex, enableHapticStatus ->
                QuickActionUiState(
                    segmentedButtonSelectedIndex = segmentedButtonValue,
                    shouldSaveSegmentedButtonIndex = shouldSaveSegmentedButtonIndex,
                    hapticStatus = enableHapticStatus
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun onEvent(event: QuickActionUiEvent){
        when(event) {
            is QuickActionUiEvent.ActiveStartButton -> {
                activeStartButton()
            }
            is QuickActionUiEvent.StopStartButton -> {
                stopStartButton()
            }

            is QuickActionUiEvent.UpdateSegmentedButtonIndex -> {
                updateSegmentedButtonIndex(event.index)
            }

            is QuickActionUiEvent.ToggleFlashLight -> {
                toggleFlashLight(event.context, event.isFlashLightOn)
            }
        }
    }

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

    private fun updateSegmentedButtonIndex(value : Int) {
        _uiState.update {
            it.copy(
                segmentedButtonSelectedIndex = value
            )
        }
        updateSegmentedButtonPreference(value)
    }

    private fun activeStartButton(){
        _uiState.update {
            it.copy(
                startButtonStatus = true
            )
        }
        deactivateSegmentedButton()
    }

    private fun stopStartButton(){
        _uiState.update {
            it.copy(
                startButtonStatus = false
            )
        }
        activateSegmentedButton()
    }

    /* ! App crash a lot
    private fun toggleFlashLight(context: Context, status: Boolean){
        val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        viewModelScope.launch {
            try {
                cameraManager.setTorchMode(cameraId, status)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }
     */

    // * New Implementation
    private fun toggleFlashLight(context: Context, status: Boolean) {
        val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as? CameraManager
        if (cameraManager == null) {
            println("CameraManager is null")
            return
        }

        val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
            try {
                val characteristics = cameraManager.getCameraCharacteristics(id)
                val hasFlash = characteristics.get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
                val isBackFacing = characteristics.get(android.hardware.camera2.CameraCharacteristics.LENS_FACING) ==
                        android.hardware.camera2.CameraCharacteristics.LENS_FACING_BACK
                hasFlash && isBackFacing
            } catch (e: Exception) {
                false
            }
        }

        if (cameraId == null) {
            println("No valid camera with flash found")
            return
        }

        viewModelScope.launch {
            try {
                cameraManager.setTorchMode(cameraId, status)
            } catch (e: CameraAccessException) {
                println("CameraAccessException: ${e.message}")
            } catch (e: Exception) {
                println("Torch error: ${e.message}")
            }
        }
    }

    private fun updateSegmentedButtonPreference(value: Int){
        viewModelScope.launch {
            if(uiState.value.shouldSaveSegmentedButtonIndex){
                userPreferencesRepository.saveSegmentedButtonValue(value)
            }else{
                userPreferencesRepository.saveSegmentedButtonValue(0)
            }
        }
    }
}