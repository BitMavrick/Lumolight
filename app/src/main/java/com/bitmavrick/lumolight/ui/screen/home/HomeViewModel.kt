package com.bitmavrick.lumolight.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState

    private val _timerValue = 10
    private var timerValue = 10
    private var timerJob : Job? = null

    init {
        viewModelScope.launch {
            userPreferencesRepository.showSosButton.collect{ value ->
                _uiState.update {
                    it.copy(
                        showSosButton = value
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeUiEvent){
        when(event){
            is HomeUiEvent.UpdateTab -> {
                updateTabIndex(event.tabIndex)
            }

            is HomeUiEvent.UpdateShowAboutDialog -> {
                updateShowAboutDialog(event.status)
            }

            is HomeUiEvent.InitializeSosTimer -> {
                initializeSosTimer()
            }

            is HomeUiEvent.CeaseSosTimer -> {
                ceaseSosTimer()
            }

            is HomeUiEvent.StopSos -> {
                stopSos()
            }
        }
    }

    private fun updateTabIndex(value: Int){
        _uiState.update {
            it.copy(
                selectedTabIndex = value
            )
        }
    }

    private fun initializeSosTimer(){
        _uiState.update {
            it.copy(
                showSosDialog = true,
                topSOSButtonStatus = TopSOSButtonStatus.COUNTING
            )
        }

        timerJob = viewModelScope.launch(Dispatchers.IO) {
            timerValue = _timerValue

            repeat(_timerValue){
                _uiState.update {
                    it.copy(
                        quickSOSCountingSeconds = timerValue
                    )
                }
                delay(1000)
                timerValue --
            }
            _uiState.update {
                it.copy(
                    topSOSButtonStatus = TopSOSButtonStatus.ACTIVE
                )
            }
        }
    }

    private fun ceaseSosTimer(){
        timerJob?.cancel()
        timerValue = _timerValue
        _uiState.update {
            it.copy(
                showSosDialog = false,
                topSOSButtonStatus = TopSOSButtonStatus.IDLE,
                quickSOSCountingSeconds = null
            )
        }
    }

    private fun stopSos(){
        ceaseSosTimer()
    }



    private fun updateShowAboutDialog(status : Boolean){
        _uiState.update {
            it.copy(
                showAboutDialog = status
            )
        }
    }
}