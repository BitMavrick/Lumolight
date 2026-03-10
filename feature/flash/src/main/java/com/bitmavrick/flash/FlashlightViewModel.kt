package com.bitmavrick.flash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.data.domain.model.LumoFlash
import com.bitmavrick.data.domain.repository.LumoFlashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashlightViewModel @Inject constructor(
    private val lumoFlashRepository: LumoFlashRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<FlashlightUiState> = combine(
        _isLoading,
        lumoFlashRepository.getAllFlash(),
        _userMessage
    ){ isLoading, list, userMessage ->
        FlashlightUiState(
            isLoading = isLoading,
            rearFlashList = list,
            userMessage = userMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FlashlightUiState(isLoading = true)
    )

    fun onEvent(event: FlashlightUiEvent){
        when(event){
            is FlashlightUiEvent.AddNewFlash -> addNewFlash(event.flash)
            is FlashlightUiEvent.UpdateFlash -> updateFlash(event.flash)
            is FlashlightUiEvent.DeleteFlash -> deleteFlash(event.flash)
            is FlashlightUiEvent.AddToHome -> addToHome(event.flash)
            is FlashlightUiEvent.RemoveFromHome -> removeFromHome(event.flash)
            is FlashlightUiEvent.ReorderBothFlash -> reorder(event.newList)
            is FlashlightUiEvent.Refresh -> refresh()
            is FlashlightUiEvent.UserMessageShown -> userMessageShown()
        }
    }

    private fun addNewFlash(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try {
                lumoFlashRepository.addNewFlash(flash)
                // _userMessage.update { "Flash added successfully" }
            } catch (e: Exception){
                _userMessage.update { "Error while adding this flash: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun updateFlash(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try {
                lumoFlashRepository.updateFlash(flash)
               //  _userMessage.update { "Flash updated successfully" }
            }catch (e: Exception){
                _userMessage.update { "Error while updating this flash: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun deleteFlash(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try {
                lumoFlashRepository.deleteFlash(flash)
               //  _userMessage.update { "Flash deleted successfully" }
            } catch (e: Exception){
                _userMessage.update { "Error while deleting this flash: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun addToHome(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try{
                lumoFlashRepository.pinnedFlash(flash)
               // _userMessage.update { "Flash pinned successfully" }
            } catch (e: Exception){
                _userMessage.update { "Error while adding to home: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun removeFromHome(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try{
                lumoFlashRepository.unPinnedFlash(flash)
               // _userMessage.update { "Flash unPinned successfully" }
            } catch (e: Exception){
                _userMessage.update { "Error while removing from home: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun reorder(newOrder: List<LumoFlash>){
        viewModelScope.launch {
            lumoFlashRepository.reorderFlash(newOrder)
        }
    }

    private fun refresh(){
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                delay(1000)
               // _userMessage.update { "Refreshed successfully" }
            } catch (e: Exception) {
                _userMessage.update { "Error refreshing: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun userMessageShown() {
        _userMessage.update { null }
    }
}