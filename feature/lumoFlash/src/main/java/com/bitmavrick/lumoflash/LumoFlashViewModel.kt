package com.bitmavrick.lumoflash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.data.domain.repository.LumoFlashRepository
import com.bitmavrick.store.preference.SettingsPreferenceRepository
import com.bitmavrick.ui.system.DefaultFlash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LumoFlashViewModel @Inject constructor(
    private val lumoFlashRepository: LumoFlashRepository,
    private val settingsPreferenceRepository: SettingsPreferenceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(LumoFlashUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val flashId: Int? = savedStateHandle["flash_id"]
        flashId?.let { getFlashById(it) }
    }

    private fun getFlashById(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                when(id){
                    -1 -> {
                        // * Default Screen Flash
                        val flash = DefaultFlash.screenFlash
                        _uiState.update { it.copy(isLoading = false, flash = flash) }
                    }

                    -2 -> {
                        // * Default Dual Flash
                        val flash = DefaultFlash.dualFlash
                        _uiState.update { it.copy(isLoading = false, flash = flash) }
                    }

                    -3 -> {
                        // * Default Rear Flash
                        val flash = DefaultFlash.rearFlash
                        _uiState.update { it.copy(isLoading = false, flash = flash) }
                    }

                    -4 -> {
                        val preference = settingsPreferenceRepository.settingsPreferenceFlow.first()
                        val flashPreference = preference.flashTilePreference

                        when(flashPreference){
                            0 -> {
                                val flash = DefaultFlash.screenFlash
                                _uiState.update { it.copy(isLoading = false, flash = flash) }
                            }

                            1 -> {
                                val flash = DefaultFlash.dualFlash
                                _uiState.update { it.copy(isLoading = false, flash = flash) }
                            }

                            2 -> {
                                val flash = DefaultFlash.rearFlash
                                _uiState.update { it.copy(isLoading = false, flash = flash) }
                            }
                        }
                    }

                    else -> {
                        val flash = lumoFlashRepository.getFlashById(id)
                        if (flash != null) {
                            _uiState.update { it.copy(isLoading = false, flash = flash) }
                        } else {
                            _uiState.update { it.copy(isLoading = false, error = "Flash not found") }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
