/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.flashlight

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Fluorescent
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.ui.screen.home.HomeUiState
import com.bitmavrick.lumolight.ui.tab.CustomFilledButton
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionUiEvent
import com.bitmavrick.lumolight.util.AppConstants
import com.bitmavrick.lumolight.util.BpmValue
import com.bitmavrick.lumolight.util.ProductionMode
import com.bitmavrick.lumolight.util.TimeDuration
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlashlightScreen(
    homeUiState: HomeUiState = HomeUiState(),
    quickActionUiEvent: (QuickActionUiEvent) -> Unit,
    flashlightUiState: FlashlightUiState = FlashlightUiState(),
    flashlightUiEvent: (FlashlightUiEvent) -> Unit,
) {
    val context = LocalContext.current
    val flashlightDurationMin = TimeDuration.list[flashlightUiState.flashlightDurationIndex].time

    var time by remember { mutableIntStateOf(flashlightDurationMin) }

    if(Build.VERSION.SDK_INT >= 33 && AppConstants.APP_PRODUCTION_MODE != ProductionMode.DEBUG){
        flashlightUiEvent(FlashlightUiEvent.UpdateMaxFlashlightStrengthIndex(context))
    }

    if(flashlightUiState.flashlightStatus){

        if(flashlightDurationMin != -1){

            time = flashlightDurationMin * 60

            LaunchedEffect(key1 = Unit) {

                repeat(flashlightDurationMin * 60) {
                    delay(1000L)
                    time--
                }

                flashlightUiEvent(FlashlightUiEvent.ToggleFlashlight(context))
                flashlightUiEvent(FlashlightUiEvent.UpdateFlashlightStatus(false))
                time = flashlightDurationMin * 60
            }
        }

        FlashAlertDialog(
            time = remember { derivedStateOf { time } },
            uiState = flashlightUiState,
            uiEvent = flashlightUiEvent,
            onClickDismiss = {
                flashlightUiEvent(FlashlightUiEvent.UpdateFlashlightStatus(false))
                flashlightUiEvent(FlashlightUiEvent.ToggleFlashlight(context))

                quickActionUiEvent(QuickActionUiEvent.StopStartButton)
                if(flashlightDurationMin != -1){
                    time = flashlightDurationMin * 60
                }
            },
            hapticStatus = homeUiState.hapticStatus
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        LazyColumn(
            Modifier.weight(1f)
        ) {
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Timelapse,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = context.getString(R.string.duration_icon_description)
                    )

                    Text(
                        text = context.getString(R.string.duration).uppercase(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Column {
                    FlowRow(
                        Modifier
                            .fillMaxWidth(1f)
                            .wrapContentHeight(align = Alignment.Top),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        TimeDuration.list.fastForEachIndexed { index, element ->
                            FilterChip(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(alignment = Alignment.CenterVertically),
                                selected = index == flashlightUiState.flashlightDurationIndex,
                                onClick = {
                                    flashlightUiEvent(FlashlightUiEvent.UpdateFlashlightDuration(index))
                                          },
                                label = { Text(element.duration) },
                                leadingIcon = if(index == flashlightUiState.flashlightDurationIndex){
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = context.getString(R.string.selected_icon_description)
                                        )
                                    }
                                } else {
                                    null
                                }
                            )
                        }
                    }
                }
            }

            item {
                HorizontalDivider(
                    Modifier.padding(vertical = 8.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Fluorescent,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = context.getString(R.string.bpm_icon_description)
                    )

                    Text(
                        text = context.getString(R.string.blink_per_minute).uppercase(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Column {
                    FlowRow(
                        Modifier
                            .fillMaxWidth(1f)
                            .wrapContentHeight(align = Alignment.Top),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        BpmValue.list.fastForEachIndexed { index, element ->
                            FilterChip(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(alignment = Alignment.CenterVertically),
                                selected = index == flashlightUiState.flashlightBpmIndex,
                                onClick = { flashlightUiEvent(FlashlightUiEvent.UpdateFlashlightBPM(index)) },
                                label = { Text(element.title) },
                                leadingIcon = if(index == flashlightUiState.flashlightBpmIndex){
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = context.getString(R.string.selected_icon_description)
                                        )
                                    }
                                } else {
                                    null
                                }
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        CustomFilledButton(
            buttonText = if(flashlightUiState.flashlightStatus) context.getString(R.string.running).uppercase() else context.getString(R.string.start).uppercase(),
            onClick = {
                flashlightUiEvent(FlashlightUiEvent.UpdateFlashlightStatus(true))
                flashlightUiEvent(FlashlightUiEvent.ToggleFlashlight(context))
            },
            hapticStatus = homeUiState.hapticStatus
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FlashlightScreenPreview(){
    FlashlightScreen(
        quickActionUiEvent = {},
        flashlightUiState = FlashlightUiState(),
        flashlightUiEvent = {},
    )
}