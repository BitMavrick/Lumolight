package com.bitmavrick.screen

import com.bitmavrick.data.domain.model.LumoFlash

sealed class ScreenFlashUiEvent {
    data class AddNewFlash(val flash: LumoFlash) : ScreenFlashUiEvent()
    data class UpdateFlash(val flash: LumoFlash) : ScreenFlashUiEvent()
    data class DeleteFlash(val flash: LumoFlash) : ScreenFlashUiEvent()
    data class AddToHome(val flash: LumoFlash) : ScreenFlashUiEvent()
    data class RemoveFromHome(val flash: LumoFlash) : ScreenFlashUiEvent()
    data class ReorderBothFlash(val newList: List<LumoFlash>) : ScreenFlashUiEvent()
    object Refresh : ScreenFlashUiEvent()
    object UserMessageShown : ScreenFlashUiEvent()

}