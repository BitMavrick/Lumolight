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
        // Combine both flows to update the UI state in one go
        viewModelScope.launch {
            combine(
                userPreferencesRepository.segmentedButtonValue,
                userPreferencesRepository.saveQuickAction
            ) { segmentedButtonValue, shouldSaveSegmentedButtonIndex ->
                QuickActionUiState(
                    segmentedButtonSelectedIndex = segmentedButtonValue,
                    shouldSaveSegmentedButtonIndex = shouldSaveSegmentedButtonIndex
                )
            }.collect { newState ->
                _uiState.value = newState
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

    fun updateSegmentedButtonIndex(value : Int) {
        _uiState.update {
            it.copy(
                segmentedButtonSelectedIndex = value
            )
        }
        updateSegmentedButtonPreference(value)
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