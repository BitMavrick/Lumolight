package com.bitmavrick.lumolight.ui.tab.screenFlash

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
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material.icons.outlined.BrightnessLow
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.ui.tab.CustomFilledButton
import com.bitmavrick.lumolight.util.BrightnessValue
import com.bitmavrick.lumolight.util.ColorValue
import com.bitmavrick.lumolight.util.TimeDuration

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScreenFlashScreen(
    viewModel: ScreenFlashViewModel,
    onClickStart: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
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
                        contentDescription = null
                    )

                    Text(
                        text = "DURATION",
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
                                selected = index == uiState.screenFlashDurationIndex,
                                onClick = { viewModel.updateScreenFlashDuration(index, element.time) },
                                label = { Text(element.duration) },
                                leadingIcon = if(index == uiState.screenFlashDurationIndex){
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

            item{
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
                        imageVector = Icons.Outlined.ColorLens,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )

                    Text(
                        text = "COLOR",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            item {
                Column {
                    FlowRow(
                        Modifier
                            .fillMaxWidth(1f)
                            .wrapContentHeight(align = Alignment.Top),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        ColorValue.list.fastForEachIndexed { index, element ->
                            FilterChip(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(alignment = Alignment.CenterVertically),
                                selected = index == uiState.screenFlashColorIndex,
                                onClick = { viewModel.updateScreenFlashColor(index, element.code) },
                                label = { Text(element.name) },
                                leadingIcon = if(index == uiState.screenFlashColorIndex){
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = "Done Icon")
                                    }
                                } else {
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.TripOrigin,
                                            tint = Color(element.code.toColorInt()),
                                            contentDescription = "Done Icon"
                                        )
                                    }
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
                        imageVector = Icons.Outlined.BrightnessLow,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )

                    Text(
                        text = "BRIGHTNESS",
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
                        BrightnessValue.list.fastForEachIndexed { index, element ->
                            FilterChip(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(alignment = Alignment.CenterVertically),
                                selected = index == uiState.screenFlashBrightnessIndex,
                                onClick = { viewModel.updateScreenFlashBrightness(index, element.value) },
                                label = { Text(element.title) },
                                leadingIcon = if(index == uiState.screenFlashBrightnessIndex){
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
        }

        Spacer(modifier = Modifier.height(8.dp))

        CustomFilledButton(
            buttonText = "start",
            onClick =  onClickStart
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenFlashScreenPreview(){
    val screenFlashViewModel : ScreenFlashViewModel = viewModel()
    ScreenFlashScreen(
        viewModel = screenFlashViewModel,
        onClickStart = {}
    )
}