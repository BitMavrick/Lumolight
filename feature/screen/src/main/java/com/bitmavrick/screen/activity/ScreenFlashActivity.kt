package com.bitmavrick.screen.activity

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bitmavrick.screen.ScreenFlashUiEvent
import com.bitmavrick.screen.ScreenFlashUiState
import com.bitmavrick.screen.ScreenFlashViewModel
import com.bitmavrick.store.preset.ColorPreset
import com.bitmavrick.ui.system.KeepScreenOn
import com.bitmavrick.ui.system.formatDuration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import com.bitmavrick.locales.R as localesR

@AndroidEntryPoint
class ScreenFlashActivity : ComponentActivity() {
    private val screenFlashViewModel: ScreenFlashViewModel by viewModels()
    private var brightnessLevel = mutableFloatStateOf(0.1f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            val context = LocalContext.current
            val screenFlashUiState by screenFlashViewModel.uiState.collectAsState()

            KeepScreenOn()
            changeBrightness(screenFlashUiState.brightness.toFloat()/100)

            ScreenFlash(
                uiState = screenFlashUiState,
                onClose = {
                    (context as? Activity)?.finish()
                }
            )
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val uiState = screenFlashViewModel.uiState.value
        val currentBrightness = uiState.brightness

        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                val newBrightness = (currentBrightness + 10).coerceAtMost(100)
                screenFlashViewModel.onEvent(ScreenFlashUiEvent.UpdateBrightness(newBrightness))
                changeBrightness(newBrightness.toFloat()/100)
                true
            }

            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                val newBrightness = (currentBrightness - 10).coerceAtLeast(0)
                screenFlashViewModel.onEvent(ScreenFlashUiEvent.UpdateBrightness(newBrightness))
                changeBrightness(newBrightness.toFloat()/100)
                true
            }

            else -> super.onKeyDown(keyCode, event)
        }
    }

    private fun changeBrightness(change: Float) {
        brightnessLevel.floatValue = change
        val layoutParams = window.attributes
        layoutParams.screenBrightness = brightnessLevel.floatValue
        window.attributes = layoutParams
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ScreenFlash(
    uiState: ScreenFlashUiState,
    onClose: () -> Unit
){
    var isScreenLocked by rememberSaveable(false) { mutableStateOf(false) }

    val backgroundColor = remember(uiState.screenColorHue, uiState.screenColorSat, uiState.screenColorVal) {
        Color.hsv(
            uiState.screenColorHue,
            uiState.screenColorSat,
            uiState.screenColorVal
        )
    }

    val duration =  uiState.duration // * in minute
    var time by rememberSaveable(duration) { mutableIntStateOf(duration * 60) }

    LaunchedEffect(duration) {
        while (time > 0) {
            delay(1000L)
            time--
        }

        delay(1000L)
        onClose()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(
            if(uiState.screenColorPresetSelection){
                Color(ColorPreset.list[uiState.screenColorPresetIndex].code.toColorInt())
            }else{
                backgroundColor
            }
        ).padding(16.dp)
    ) {

        if(isScreenLocked){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = formatDuration(time),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )

                IconButton(
                    onClick = {
                        isScreenLocked = !isScreenLocked
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LockOpen,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                IconButton(
                    onClick = {
                        isScreenLocked = !isScreenLocked
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(225.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val animatedProgress by animateFloatAsState(
                        targetValue = (time / (duration.toFloat() * 60)).coerceIn(0f, 1f),
                        animationSpec = tween(1000, easing = LinearEasing)
                    )

                    CircularWavyProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        progress = { animatedProgress },
                        waveSpeed = 5.dp,
                        wavelength = 30.dp,
                        amplitude = { 1f }
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            imageVector = Icons.Default.Brightness7,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )

                        Text(
                            text = formatDuration(time),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )

                        Text(
                            text = "Brightness: ${uiState.brightness}%",
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

                Spacer(Modifier.padding(8.dp))
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClose()
                }
            ) {
                Text(stringResource(localesR.string.exit))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenFlashPreview(){
    ScreenFlash(
        uiState = ScreenFlashUiState(),
        onClose = {}
    )
}