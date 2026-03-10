package com.bitmavrick.both

import com.bitmavrick.data.domain.model.LumoFlash

sealed class BothFlashUiEvent {
    data class AddNewFlash(val flash: LumoFlash) : BothFlashUiEvent()
    data class UpdateFlash(val flash: LumoFlash) : BothFlashUiEvent()
    data class DeleteFlash(val flash: LumoFlash) : BothFlashUiEvent()
    data class AddToHome(val flash: LumoFlash) : BothFlashUiEvent()
    data class RemoveFromHome(val flash: LumoFlash) : BothFlashUiEvent()
    data class ReorderBothFlash(val newList: List<LumoFlash>) : BothFlashUiEvent()
    object Refresh : BothFlashUiEvent()
    object UserMessageShown : BothFlashUiEvent()
}