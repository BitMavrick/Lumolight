/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context

sealed interface QuickActionUiEvent {
    data object ActiveStartButton : QuickActionUiEvent
    data object StopStartButton : QuickActionUiEvent
    data class ToggleFlashLight(val context: Context, val isFlashLightOn: Boolean) : QuickActionUiEvent
    data class UpdateSegmentedButtonIndex(val index: Int) : QuickActionUiEvent
}