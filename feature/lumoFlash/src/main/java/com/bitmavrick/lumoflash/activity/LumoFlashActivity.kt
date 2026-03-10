package com.bitmavrick.lumoflash.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bitmavrick.lumoflash.LumoFlashBody
import com.bitmavrick.lumoflash.LumoFlashViewModel
import com.bitmavrick.settings.SettingsViewModel
import com.bitmavrick.ui.system.KeepScreenOn
import com.bitmavrick.ui.system.deactivateFlashlight
import com.bitmavrick.ui.system.getMaxFlashlightStrengthValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LumoFlashActivity : ComponentActivity() {
    private val viewModel: LumoFlashViewModel by viewModels()
    private val settingsViewModel : SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            val context = LocalContext.current
            KeepScreenOn()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            val settingsUiState by settingsViewModel.uiState.collectAsState()

            this.volumeKeyFlashControl = settingsUiState.volumeKeyFlashControl

            state.flash?.let { flash ->
                var brightness by remember(flash) { mutableFloatStateOf(flash.screenBrightness ?: 100f) }

                this.brightness = { brightness }
                this.onBrightnessChange = { brightness = it }

                LumoFlashBody(
                    flash = flash,
                    brightness = brightness,
                    onBrightnessChange = { brightness = it },
                    isPremium = settingsUiState.lumolightPremium,
                    flashMaxIntensityLevel = getMaxFlashlightStrengthValue(context),
                    onClose = {
                        deactivateFlashlight(context)
                        (context as? Activity)?.finish()
                    }
                )
            }
        }
    }

    private var brightness: (() -> Float)? = null
    private var onBrightnessChange: ((Float) -> Unit)? = null
    private var volumeKeyFlashControl: Boolean = false

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (!volumeKeyFlashControl) return super.onKeyDown(keyCode, event)
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP,
            KeyEvent.KEYCODE_VOLUME_DOWN -> true
            else -> super.onKeyDown(keyCode, event)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (!volumeKeyFlashControl) return super.onKeyUp(keyCode, event)
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                brightness?.invoke()?.let { current ->
                    onBrightnessChange?.invoke((current + 10f).coerceAtMost(100f))
                }
                true
            }

            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                brightness?.invoke()?.let { current ->
                    onBrightnessChange?.invoke((current - 10f).coerceAtLeast(0f))
                }
                true
            }

            else -> super.onKeyUp(keyCode, event)
        }
    }
}