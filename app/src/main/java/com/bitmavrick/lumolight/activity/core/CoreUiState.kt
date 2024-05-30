package com.bitmavrick.lumolight.activity.core

import com.bitmavrick.lumolight.ui.screen.setting.Appearance

data class CoreUiState (
    val appLoading: Boolean = false,
    val appearance: Appearance = Appearance.DEFAULT,
    val dynamicTheme: Boolean = true
)