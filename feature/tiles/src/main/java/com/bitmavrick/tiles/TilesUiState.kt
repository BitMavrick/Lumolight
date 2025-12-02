package com.bitmavrick.tiles

import com.bitmavrick.data.domain.model.ScreenColor

data class TilesUiState (
    val isLoading: Boolean = false,
    val colors: List<ScreenColor> = emptyList(),
    val error: String? = null
)