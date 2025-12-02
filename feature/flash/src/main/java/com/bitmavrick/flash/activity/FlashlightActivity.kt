package com.bitmavrick.flash.activity

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bitmavrick.flash.FlashlightUiEvent
import com.bitmavrick.flash.FlashlightUiState
import com.bitmavrick.flash.FlashlightViewModel
import com.bitmavrick.ui.system.KeepScreenOn
import com.bitmavrick.ui.system.activateFlashlight
import com.bitmavrick.ui.system.activateFlashlightWithIntensity
import com.bitmavrick.ui.system.deactivateFlashlight
import com.bitmavrick.ui.system.formatDuration
import com.bitmavrick.ui.system.getMaxFlashlightStrengthValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import com.bitmavrick.locales.R as localesR

@AndroidEntryPoint
class FlashlightActivity : ComponentActivity() {
    private val flashlightViewModel : FlashlightViewModel by viewModels()
    private val maxIntensityLevel = mutableIntStateOf(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            KeepScreenOn()

            val context = LocalContext.current
            val flashlightUiState by flashlightViewModel.uiState.collectAsState()

            maxIntensityLevel.intValue = getMaxFlashlightStrengthValue(context)

            FlashLight(
                uiState = flashlightUiState,
                flashMaxIntensityLevel = maxIntensityLevel.intValue,
                onClose = {
                    deactivateFlashlight(context)
                    (context as? Activity)?.finish()
                }
            )
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val uiState = flashlightViewModel.uiState.value
        val currentIntensity = uiState.intensity

        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                val newIntensity = (currentIntensity + 1).coerceAtMost(maxIntensityLevel.intValue)
                flashlightViewModel.onEvent(FlashlightUiEvent.UpdateFlashlightIntensity(newIntensity))
                true
            }

            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                val newIntensity = (currentIntensity - 1).coerceAtLeast(1)
                flashlightViewModel.onEvent(FlashlightUiEvent.UpdateFlashlightIntensity(newIntensity))
                true
            }

            else -> super.onKeyDown(keyCode, event)
        }
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FlashLight(
    uiState: FlashlightUiState,
    flashMaxIntensityLevel: Int,
    onClose: () -> Unit
){
    var isScreenLocked by rememberSaveable(false) { mutableStateOf(false) }

    val context = LocalContext.current
    val duration =  uiState.duration // in minute
    var time by rememberSaveable(duration) { mutableIntStateOf(duration * 60) }
    var active by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(duration) {
        while (time > 0) {
            delay(1000L)
            time--
        }

        delay(1000L)
        onClose()
    }

    LaunchedEffect(uiState, active) {
        if(flashMaxIntensityLevel > 1){
            activateFlashlightWithIntensity(
                active = active,
                context = context,
                intensity = uiState.intensity,
                bpm = uiState.bpm,
                maxIntensity = flashMaxIntensityLevel
            )

        }else{
            activateFlashlight(
                active = active,
                context = context,
                bpm = uiState.bpm
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(
            color = Color.Black
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
        }else{
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
                        color = MaterialTheme.colorScheme.tertiary,
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
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )

                        Text(
                            text = formatDuration(time),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        if(flashMaxIntensityLevel > 1){
                            Text(
                                text = "Intensity level: ${uiState.intensity} / $flashMaxIntensityLevel",
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.padding(2.dp)
                            )
                        }

                        Text(
                            text = "BPM: ${uiState.bpm}",
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    active = false
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
private fun FlashlightPreview(){
    FlashLight(
        uiState = FlashlightUiState(),
        flashMaxIntensityLevel = 1,
        onClose = {}
    )
}