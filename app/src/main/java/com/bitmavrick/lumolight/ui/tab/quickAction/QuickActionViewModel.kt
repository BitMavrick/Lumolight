package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    fun toggleFlashLight(context: Context, status: Boolean){
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
}