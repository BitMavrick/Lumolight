package com.bitmavrick.lumolight.ui.screen.quickActions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuickActionsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuickActionsUiState())
    val uiState : StateFlow<QuickActionsUiState> = _uiState

    private val _timerValue = 10
    private var timerValue = 10
    private var timerJob : Job? = null

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

    fun loadingStartButtonWithTimer(seconds: Int){
        _uiState.update {
            it.copy(
                startButtonLittleLoading = true
            )
        }
        viewModelScope.launch {
            repeat(seconds){
                delay(1000)
            }

            _uiState.update {
                it.copy(
                    startButtonLittleLoading = false
                )
            }
        }
    }

    fun updateSegmentedButtonStatus(value : Int){
        _uiState.update {
            it.copy(
                segmentedButtonIndex = value
            )
        }
    }

    fun runSOSTimer(){
        _uiState.update {
            it.copy(
                quickSOSButtonStatus = QuickSOSButtonStatus.RUNNING
            )
        }

        timerJob = viewModelScope.launch(Dispatchers.IO) {
            timerValue = _timerValue

            repeat(_timerValue){
                _uiState.update {
                    it.copy(
                        quickSOSRunningSeconds = timerValue
                    )
                }
                delay(1000)
                timerValue --
            }
            _uiState.update {
                it.copy(
                    quickSOSButtonStatus = QuickSOSButtonStatus.ACTIVE
                )
            }
        }
    }

    fun cancelSOSTimer(){
        timerJob?.cancel()
        timerValue = _timerValue
        _uiState.update {
            it.copy(
                quickSOSButtonStatus = QuickSOSButtonStatus.NONE,
                quickSOSRunningSeconds = null
            )
        }
    }

    fun stopSOS(){
        _uiState.update {
            timerJob?.cancel()
            timerValue = _timerValue
            it.copy(
                quickSOSButtonStatus = QuickSOSButtonStatus.NONE,
                quickSOSRunningSeconds = null
            )
        }
    }
}