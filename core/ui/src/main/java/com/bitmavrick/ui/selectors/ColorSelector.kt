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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Square
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.ui.presets.ColorPresets
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.launch
import com.bitmavrick.ui.R as IconR
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ColorSelectorDialog(
    initialColor: Color = Color.White,
    onConfirm: (color: Color) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val controller = rememberColorPickerController()
    var color by remember { mutableStateOf(initialColor) }

    AlertDialog(
        icon = {
            Icon(
                painter = painterResource(id = IconR.drawable.icon_color),
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(localesR.string.color),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        onDismissRequest = onCancel,
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onConfirm(color)
                }
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
                modifier = Modifier
                    .width(300.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 2 }
                )

                HorizontalPager(
                    state = pagerState
                ) { page ->
                    when(page){
                        0 -> {
                            Column(
                                modifier = Modifier.fillMaxWidth().height(300.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = color.toArgb().toHexColor(),
                                    color = color,
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                HsvColorPicker(
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f)
                                        .height(200.dp),
                                    controller = controller,
                                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                                        color = colorEnvelope.color // ARGB color value.
                                    },
                                    initialColor = color
                                )

                                BrightnessSlider(
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f)
                                        .height(35.dp),
                                    borderRadius = 16.dp,
                                    controller = controller,
                                    initialColor = color
                                )
                            }
                        }

                        1 -> {
                            Column(
                                modifier = Modifier.fillMaxWidth().height(300.dp),
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(100.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val colors = ColorPresets.colors

                                    items(
                                        items = colors,
                                        key = { it.code },
                                        contentType = { "color" }
                                    ){
                                        val presetColor = it.code.toColor()

                                        Card(
                                            modifier = Modifier.padding(vertical = 4.dp),
                                            border = if(color == presetColor){
                                                BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                                            } else {
                                                null
                                            }
                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxSize().height(42.dp).clickable(
                                                    onClick = {
                                                        color = presetColor
                                                        controller.selectByColor(presetColor, true)
                                                    }
                                                ),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(horizontal = 16.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ){
                                                    Icon(
                                                        imageVector = Icons.Rounded.Square,
                                                        contentDescription = null,
                                                        tint = presetColor
                                                    )

                                                    Text(
                                                        text = it.name,
                                                        modifier = Modifier.padding(start = 8.dp),
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
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

fun Int.toHexColor(): String {
    return String.format("#%06X", 0xFFFFFF and this)
}

fun String.toColor(): Color {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        Color.White
    }
}

@PreviewLightDark
@Composable
private fun ColorSelectorDialogPreview(){
    LumolightTheme {
        ColorSelectorDialog()
    }
}