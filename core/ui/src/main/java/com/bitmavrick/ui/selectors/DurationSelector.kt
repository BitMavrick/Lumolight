package com.bitmavrick.ui.selectors

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.ui.components.Picker
import com.bitmavrick.ui.presets.DurationPresets
import com.bitmavrick.ui.system.formatDuration
import kotlinx.coroutines.launch
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DurationSelectorDialog(
    initialSeconds: Int = 120,
    onConfirm: (totalSeconds: Int) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var minutes by remember { mutableIntStateOf(initialSeconds / 60) }
    var seconds by remember { mutableIntStateOf(initialSeconds % 60) }

    val minutesList = remember { (0..99).map { it.toString().padStart(2, '0') } }
    val secondsList = remember { (0..59).map { it.toString().padStart(2, '0') } }

    AlertDialog(
        onDismissRequest = onCancel,
        icon = {
            Icon(
                imageVector = Icons.Rounded.Timelapse,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(localesR.string.duration),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        confirmButton = {
            FilledTonalButton(
                onClick = { onConfirm(minutes * 60 + seconds) }
            ) {
                Text(stringResource(localesR.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(localesR.string.cancel))
            }
        },
        text = {
            Column(
                modifier = Modifier.width(300.dp)
            ){
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 2 }
                )

                HorizontalPager(
                    state = pagerState
                ) { page ->
                    when(page){
                        0 -> {
                            Row(
                                modifier = Modifier.height(200.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Picker(
                                        items = minutesList,
                                        initialIndex = minutes,
                                        modifier = Modifier.width(80.dp),
                                        onItemSelected = { minutes = it }
                                    )
                                    Text(
                                        text = stringResource(localesR.string.minutes),
                                        style = MaterialTheme.typography.labelMedium,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }

                                Text(
                                    text = ":",
                                    style = MaterialTheme.typography.headlineLarge,
                                    modifier = Modifier.padding(horizontal = 8.dp).padding(bottom = 30.dp)
                                )

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Picker(
                                        items = secondsList,
                                        initialIndex = seconds,
                                        modifier = Modifier.width(80.dp),
                                        onItemSelected = { seconds = it }
                                    )
                                    Text(
                                        text = stringResource(localesR.string.seconds),
                                        style = MaterialTheme.typography.labelMedium,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }

                            }
                        }

                        1 -> {
                            Column(
                                modifier = Modifier.height(200.dp)
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(100.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val durations = DurationPresets.duration

                                    items(
                                        items = durations,
                                        key = { it },
                                        contentType = { "duration" }
                                    ){
                                        Card(
                                            modifier = Modifier.padding(vertical = 4.dp),
                                            border = if( it == minutes * 60 + seconds){
                                                BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                                            } else {
                                                null
                                            }
                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxSize().height(38.dp).clickable(
                                                    onClick = {
                                                        minutes = it / 60
                                                        seconds = it % 60
                                                    }
                                                ),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = formatDuration(it) + " min",
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                val nextPage = if (pagerState.currentPage == 0) 1 else 0
                                pagerState.animateScrollToPage(nextPage)
                            }
                        },
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text(
                            text = if (pagerState.currentPage == 0) stringResource(localesR.string.presets) else stringResource(localesR.string.manual)
                        )
                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun DurationSelectorDialogPreview(){
    LumolightTheme{
        DurationSelectorDialog()
    }
}