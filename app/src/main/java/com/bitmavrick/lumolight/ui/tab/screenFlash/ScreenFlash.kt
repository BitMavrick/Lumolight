/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.screenFlash

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.graphics.toColorInt
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.ui.screen.home.HomeUiState
import com.bitmavrick.lumolight.ui.tab.CustomFilledButton
import com.bitmavrick.lumolight.util.BrightnessValue
import com.bitmavrick.lumolight.util.ColorValue
import com.bitmavrick.lumolight.util.TimeDuration


@SuppressLint("NewApi")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScreenFlashScreen(
    homeUiState: HomeUiState = HomeUiState(),
    screenFlashUiState: ScreenFlashUiState = ScreenFlashUiState(),
    screenFlashUiEvent: (ScreenFlashUiEvent) -> Unit,
    onClickStart: () -> Unit
) {
    val context = LocalContext.current

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
                                selected = index == screenFlashUiState.screenFlashDurationIndex,
                                onClick = {
                                    screenFlashUiEvent(ScreenFlashUiEvent.UpdateScreenFlashDuration(index)) },
                                label = { Text(element.duration) },
                                leadingIcon = if(index == screenFlashUiState.screenFlashDurationIndex){
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
                        contentDescription = context.getString(R.string.color_icon_description)
                    )

                    Text(
                        text = context.getString(R.string.color).uppercase(),
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
                                selected = index == screenFlashUiState.screenFlashColorIndex,
                                onClick = { screenFlashUiEvent(ScreenFlashUiEvent.UpdateScreenFlashColor(index)) },
                                label = { Text(element.name) },
                                leadingIcon = if(index == screenFlashUiState.screenFlashColorIndex){
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            tint = Color(element.code.toColorInt()),
                                            contentDescription = context.getString(R.string.selected_icon_description)
                                        )
                                    }
                                } else {
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.TripOrigin,
                                            tint = Color(element.code.toColorInt()),
                                            contentDescription = context.getString(R.string.color_preview_icon_description)
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
                        contentDescription = context.getString(R.string.brightness_icon_description)
                    )

                    Text(
                        text = context.getString(R.string.brightness).uppercase(),
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
                                selected = index == screenFlashUiState.screenFlashBrightnessIndex,
                                onClick = { screenFlashUiEvent(ScreenFlashUiEvent.UpdateScreenFlashBrightness(index)) },
                                label = { Text(element.title) },
                                leadingIcon = if(index == screenFlashUiState.screenFlashBrightnessIndex){
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

        Spacer(modifier = Modifier.height(8.dp))

        CustomFilledButton(
            colorIndex = screenFlashUiState.screenFlashColorIndex,
            buttonText = context.getString(R.string.start),
            onClick =  onClickStart,
            hapticStatus = homeUiState.hapticStatus
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenFlashScreenPreview(){
    ScreenFlashScreen(
        homeUiState = HomeUiState(),
        screenFlashUiState = ScreenFlashUiState(),
        screenFlashUiEvent = {},
        onClickStart = {}
    )
}