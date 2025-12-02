package com.bitmavrick.tiles

import com.bitmavrick.data.domain.model.ScreenColor

sealed class TilesUiEvent {
    object LoadColors : TilesUiEvent()
    data class AddColor(val title: String, val colorCode: String) : TilesUiEvent()
    data class DeleteColor(val color: ScreenColor) : TilesUiEvent()
    data class UpdateColor(val color: ScreenColor) : TilesUiEvent()
}