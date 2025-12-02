package com.bitmavrick.shortcuts.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.CircularProgressIndicator
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
import com.bitmavrick.flash.FlashlightUiState
import com.bitmavrick.flash.FlashlightViewModel
import com.bitmavrick.screen.ScreenFlashUiState
import com.bitmavrick.screen.ScreenFlashViewModel
import com.bitmavrick.store.preset.ColorPreset
import com.bitmavrick.ui.system.KeepScreenOn
import com.bitmavrick.ui.system.activateFlashlight
import com.bitmavrick.ui.system.activateFlashlightWithIntensity
import com.bitmavrick.ui.system.deactivateFlashlight
import com.bitmavrick.ui.system.getMaxFlashlightStrengthValue
import dagger.hilt.android.AndroidEntryPoint
import com.bitmavrick.locales.R as localesR

@AndroidEntryPoint
class BothFlashActivity : ComponentActivity() {
    private val screenFlashViewModel: ScreenFlashViewModel by viewModels()
    private val flashlightViewModel: FlashlightViewModel by viewModels()

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
            val flashlightUiState by flashlightViewModel.uiState.collectAsState()

            KeepScreenOn()
            setBrightness(screenFlashUiState.brightness.toFloat() / 100)

            BothFlashScreen(
                screenFlashUiState = screenFlashUiState,
                flashlightUiState = flashlightUiState,
                onClose = {
                    deactivateFlashlight(context)
                    (context as? Activity)?.finish()
                }
            )
        }
    }

    private fun setBrightness(value: Float){
        val layoutParams = window.attributes
        layoutParams.screenBrightness = value
        window.attributes = layoutParams
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun BothFlashScreen(
    screenFlashUiState: ScreenFlashUiState,
    flashlightUiState: FlashlightUiState,
    onClose: () -> Unit
){
    val context = LocalContext.current
    var isScreenLocked by rememberSaveable(false) { mutableStateOf(false) }
    var isFlashlightActive by rememberSaveable { mutableStateOf(true) }

    val backgroundColor = remember(screenFlashUiState.screenColorHue, screenFlashUiState.screenColorSat, screenFlashUiState.screenColorVal) {
        Color.hsv(
            screenFlashUiState.screenColorHue,
            screenFlashUiState.screenColorSat,
            screenFlashUiState.screenColorVal
        )
    }

    LaunchedEffect(flashlightUiState, isFlashlightActive) {
        if(flashlightUiState.intensity > 1){
            activateFlashlightWithIntensity(
                active = isFlashlightActive,
                context = context,
                intensity = flashlightUiState.intensity,
                bpm = flashlightUiState.bpm,
                maxIntensity = getMaxFlashlightStrengthValue(context)
            )

        }else{
            activateFlashlight(
                active = isFlashlightActive,
                context = context,
                bpm = flashlightUiState.bpm
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(
            color = if(screenFlashUiState.screenColorPresetSelection){
                Color(ColorPreset.list[screenFlashUiState.screenColorPresetIndex].code.toColorInt())

            }else {
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
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        progress = { 1f }
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Icon(
                                imageVector = Icons.Default.Brightness7,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )

                            Spacer(Modifier.padding(4.dp))

                            Text(
                                text = "+",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(Modifier.padding(1.dp))

                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }

                        Text(
                            text = "Brightness: ${screenFlashUiState.brightness}%",
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(2.dp)
                        )

                        Text(
                            text = "BPM: ${flashlightUiState.bpm}",
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
                    isFlashlightActive = false
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
private fun BothFlashScreenPreview(){
    BothFlashScreen(
        screenFlashUiState = ScreenFlashUiState(),
        flashlightUiState = FlashlightUiState(),
        onClose = {}
    )
}