package com.bitmavrick.lumoflash

import com.bitmavrick.data.domain.model.LumoFlash

data class LumoFlashUiState(
    val isLoading: Boolean = false,
    val flash: LumoFlash? = null,
    val error: String? = null
)
