package com.bitmavrick.lumolight.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    }

    private fun ceaseSosTimer(){

    }

    private fun stopSos(){

    }



    private fun updateShowAboutDialog(status : Boolean){
        _uiState.update {
            it.copy(
                showAboutDialog = status
            )
        }
    }
}