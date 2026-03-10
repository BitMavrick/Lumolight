package com.bitmavrick.flash

import com.bitmavrick.data.domain.model.LumoFlash

sealed class FlashlightUiEvent {
    data class AddNewFlash(val flash: LumoFlash) : FlashlightUiEvent()
    data class UpdateFlash(val flash: LumoFlash) : FlashlightUiEvent()
    data class DeleteFlash(val flash: LumoFlash) : FlashlightUiEvent()
    data class AddToHome(val flash: LumoFlash) : FlashlightUiEvent()
    data class RemoveFromHome(val flash: LumoFlash) : FlashlightUiEvent()
    data class ReorderBothFlash(val newList: List<LumoFlash>) : FlashlightUiEvent()
    object Refresh : FlashlightUiEvent()
    object UserMessageShown : FlashlightUiEvent()
}