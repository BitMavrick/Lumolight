package com.bitmavrick.lumoflash

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.data.domain.model.LumoFlash
import com.bitmavrick.ui.components.LumoTag
import com.bitmavrick.ui.selectors.toColor
import com.bitmavrick.ui.system.SetBrightness
import com.bitmavrick.ui.system.activateFlashlight
import com.bitmavrick.ui.system.activateFlashlightWithIntensity
import com.bitmavrick.ui.system.formatDuration
import com.bitmavrick.ui.system.hasFlashlight
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.BrightnessUp
import kotlinx.coroutines.delay
import com.bitmavrick.locales.R as localesR
import com.bitmavrick.ui.R as IconR

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LumoFlashBody(
    flash: LumoFlash,
    brightness: Float,
    onBrightnessChange: (Float) -> Unit,
    flashMaxIntensityLevel: Int = 1,
    isPremium: Boolean,
    onClose: () -> Unit
) {

    val context = LocalContext.current
    var isScreenLocked by rememberSaveable(false) { mutableStateOf(false) }

    var intensity by remember { mutableIntStateOf(flash.flashIntensity ?: 1) }
    val duration = flash.duration

    var time by rememberSaveable(duration) { mutableIntStateOf(duration) }
    var active by rememberSaveable { mutableStateOf(true) }

    if (flash.flashType == 0 || flash.flashType == 1) {
        SetBrightness(
            level = (brightness / 100f),
            context = context
        )
    }

    if(!flash.infiniteDuration){
        LaunchedEffect(duration) {
            while (time > 0) {
                delay(1000L)
                time--
            }

            delay(1000L)
            onClose()
        }
    }

    LaunchedEffect(flash, active, intensity) {
        if(hasFlashlight(context) && (flash.flashType == 1 || flash.flashType == 2)){
            if(flashMaxIntensityLevel > 1){
                activateFlashlightWithIntensity(
                    active = active,
                    context = context,
                    intensity = intensity,
                    bpm = flash.flashBpmRate ?: 0,
                    maxIntensity = flashMaxIntensityLevel
                )

            } else {
                activateFlashlight(
                    active = active,
                    context = context,
                    bpm = flash.flashBpmRate ?: 0
                )
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().background(
            color = if(flash.flashType == 2) Color.Black else flash.screenColor?.toColor() ?: Color.White
        ).padding(16.dp)
    ) {
        if(isScreenLocked){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                if(!flash.infiniteDuration){
                    Text(
                        text = formatDuration(time),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

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
                    modifier = Modifier.size(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val animatedProgress by animateFloatAsState(
                        targetValue = (time / (duration.toFloat())).coerceIn(0f, 1f),
                        animationSpec = tween(1000, easing = LinearEasing)
                    )

                    CircularWavyProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.tertiary,
                        progress = { animatedProgress },
                        waveSpeed = 5.dp,
                        wavelength = 40.dp,
                        amplitude = { 1f }
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        when (flash.flashType) {
                            0 -> {
                                Icon(
                                    imageVector = TablerIcons.BrightnessUp,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(32.dp),
                                )
                            }
                            1 -> {
                                Icon(
                                    painter = painterResource(id = IconR.drawable.icon_both_flash),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(32.dp),
                                )
                            }
                            2 -> {
                                Icon(
                                    imageVector = TablerIcons.Bolt,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(32.dp),
                                )
                            }
                        }

                        Text(
                            text = if(flash.infiniteDuration) "INFINITE" else  formatDuration(time),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        if(flash.flashType == 0 || flash.flashType == 1){
                            LumoTag(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                icon = TablerIcons.BrightnessUp,
                                text = flash.screenColor + " • " + brightness.toInt().toString() + "%",
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }


                        if(flash.flashType == 1 || flash.flashType == 2 && hasFlashlight(context)){
                            LumoTag(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                icon = TablerIcons.Bolt,
                                text = flash.flashBpmRate.toString() + " BPM • Lvl " + intensity,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        if(flash.flashType == 0){
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilledTonalIconButton(
                                    onClick = {
                                        onBrightnessChange((brightness - 10f).coerceIn(0f, 100f))
                                    }
                                ) {
                                    Text(
                                        text = "-10",
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Icon(
                                    imageVector = TablerIcons.BrightnessUp,
                                    contentDescription = null
                                )

                                FilledTonalIconButton(
                                    onClick = {
                                        onBrightnessChange((brightness + 10f).coerceIn(0f, 100f))
                                    }
                                ) {
                                    Text(
                                        text = "+10",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        if(flash.flashType == 2 && flashMaxIntensityLevel > 1){

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                FilledTonalIconButton(
                                    onClick = {
                                        intensity = (intensity - 1).coerceAtLeast(1)
                                    }
                                ) {
                                    Text(
                                        text = "-1",
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Icon(
                                    imageVector = Icons.Default.FlashOn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )

                                FilledTonalIconButton(
                                    onClick = {
                                        intensity = (intensity + 1).coerceAtMost(flashMaxIntensityLevel)
                                    }
                                ) {
                                    Text(
                                        text = "+1",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
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

            if((flash.flashType == 1 || flash.flashType == 2) && !hasFlashlight(context)){
                Text(
                    text = "Unable to detect camera flash hardware!",
                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }

    BackHandler {
        active = false
        onClose()
    }
}


@Preview(showBackground = true)
@Composable
private fun LumoFlashPreview(){
    val flashShort = LumoFlash(
        title = "Short title",
        flashType = 2,
        isPinned = false,
        duration = 60,
        infiniteDuration = false,
        screenColor = "#FFFFFF",
        screenBrightness = 10f,
        flashBpmRate = 0,
        flashIntensity = 3,
        pinnedOrderIndex = null
    )

    LumoFlashBody(
        flash = flashShort,
        brightness = 10f,
        onBrightnessChange = {},
        flashMaxIntensityLevel = 1,
        isPremium = false,
        onClose = {}
    )
}