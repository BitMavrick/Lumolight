/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.flashlight

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.ui.tab.CustomFilledButton
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionUiEvent
import com.bitmavrick.lumolight.util.BpmValue
import com.bitmavrick.lumolight.util.TimeDuration
import com.bitmavrick.lumolight.util.formatDuration
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlashlightScreen(
    quickActionUiEvent: (QuickActionUiEvent) -> Unit,
    flashlightViewModel: FlashlightViewModel,
) {
    val context = LocalContext.current
    val uiState = flashlightViewModel.uiState.collectAsState().value

    var time by remember { mutableIntStateOf(uiState.flashlightDurationMin) }

    if(uiState.flashlightStatus){

        if(uiState.flashlightDurationMin != -1){

            time = uiState.flashlightDurationMin * 60

            LaunchedEffect(key1 = Unit) {
                for (i in 1..uiState.flashlightDurationMin * 60) {
                    delay(1000L)
                    time--
                }
                flashlightViewModel.toggleFlashLight(context, false)
                flashlightViewModel.updateFlashlightAlert(false)
                time = uiState.flashlightDurationMin * 60
            }
        }

        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = { Text(text = context.getString(R.string.flashlight_active)) },
            text = {
                if(uiState.flashlightDurationMin != -1){
                    Text(
                        text = "Duration: ${formatDuration(time)}\nBlink per min: ${uiState.flashlightBpmValue}"
                    )
                }else{
                    Text(
                        text = "Duration: N/A\nBlink per min: ${uiState.flashlightBpmValue}"
                    )
                }
            },
            dismissButton = {
                CustomFilledButton(
                    buttonText = context.getString(R.string.stop).uppercase(),
                    onClick = {
                        flashlightViewModel.toggleFlashLight(context, false)
                        flashlightViewModel.updateFlashlightAlert(false)
                        quickActionUiEvent(QuickActionUiEvent.StopStartButton)
                        if(uiState.flashlightDurationMin != -1){
                            time = uiState.flashlightDurationMin * 60
                        }
                    }
                )
            }
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
                                selected = index == uiState.flashlightDurationIndex,
                                onClick = { flashlightViewModel.updateFlashlightDuration(index, element.time) },
                                label = { Text(element.duration) },
                                leadingIcon = if(index == uiState.flashlightDurationIndex){
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
                                selected = index == uiState.flashlightBpmIndex,
                                onClick = { flashlightViewModel.updateFlashlightBpm(index, element.value) },
                                label = { Text(element.title) },
                                leadingIcon = if(index == uiState.flashlightBpmIndex){
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

            /* !! Maybe in the upcoming versions

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
                        imageVector = Icons.Outlined.Highlight,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )

                    Text(
                        text = "INTENSITY",
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
                        IntensityValue.list.fastForEachIndexed { index, element ->
                            FilterChip(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(alignment = Alignment.CenterVertically),
                                selected = index == uiState.flashlightIntensityIndex,
                                onClick = { viewModel.updateFlashlightIntensity(index, element.value) },
                                label = { Text(element.title) },
                                leadingIcon = if(index == uiState.flashlightIntensityIndex){
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = "Done Icon")
                                    }
                                } else {
                                    null
                                }
                            )
                        }
                    }
                }
            }

            */
        }

        /* !! Maybe in the upcoming version
        Spacer(modifier = Modifier.height(16.dp))
        FlashlightMorseCodeButton()
        */
        Spacer(modifier = Modifier.height(16.dp))
        CustomFilledButton(
            buttonText = if(uiState.flashlightStatus) context.getString(R.string.running).uppercase() else context.getString(R.string.start).uppercase(),
            onClick = {
                flashlightViewModel.updateFlashlightAlert(true)
                flashlightViewModel.toggleFlashLight(context, true)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FlashlightScreenPreview(){
    val flashlightViewModel : FlashlightViewModel = viewModel()
    FlashlightScreen(
        quickActionUiEvent = {},
        flashlightViewModel = flashlightViewModel
    )
}