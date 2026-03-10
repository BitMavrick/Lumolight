package com.bitmavrick.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitmavrick.data.domain.model.LumoFlash
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.ui.panels.DurationPanel
import com.bitmavrick.ui.panels.FlashPanel
import com.bitmavrick.ui.panels.ScreenFlashPanel
import com.bitmavrick.ui.panels.TitlePanel
import com.bitmavrick.ui.selectors.toColor
import com.bitmavrick.ui.selectors.toHexColor
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.BrightnessUp
import com.bitmavrick.ui.R as IconR
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashDetailsBottomSheet(
    updateFlash: LumoFlash? = null,
    flashType: Int,
    onCreate: (LumoFlash) -> Unit = {},
    onUpdate: (LumoFlash) -> Unit = {},
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var flashTitle by remember { mutableStateOf("") }
    var flashColor by remember { mutableStateOf(Color.White) }
    var flashBrightness by remember { mutableIntStateOf(60) }
    var flashBpm by remember { mutableIntStateOf(0) }
    var flashDuration by remember { mutableIntStateOf(300) }
    var infiniteDuration by remember { mutableStateOf(false) }
    var intensity by remember { mutableIntStateOf(1) }

    if(updateFlash != null){
        flashTitle = updateFlash.title
        flashColor = updateFlash.screenColor?.toColor() ?: Color.White
        flashBrightness = updateFlash.screenBrightness?.toInt() ?: 0
        flashBpm = updateFlash.flashBpmRate ?: 0
        flashDuration = updateFlash.duration
        infiniteDuration = updateFlash.infiniteDuration
        intensity = updateFlash.flashIntensity ?: 1
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outlineVariant) },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        when (flashType) {
                            0 -> {
                                Icon(
                                    imageVector = TablerIcons.BrightnessUp,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            1 -> {
                                Icon(
                                    painter = painterResource(id = IconR.drawable.icon_both_flash),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            2 -> {
                                Icon(
                                    imageVector = TablerIcons.Bolt,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                when (flashType) {
                    0 -> {
                        Text(
                            text = if (updateFlash == null) stringResource(localesR.string.new_screen_flash) else stringResource(localesR.string.edit_screen_flash),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    1 -> {
                        Text(
                            text = if (updateFlash == null) stringResource(localesR.string.new_dual_flash) else stringResource(localesR.string.edit_dual_flash),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    2 -> {
                        Text(
                            text = if (updateFlash == null) stringResource(localesR.string.new_rear_flash) else stringResource(localesR.string.edit_rear_flash),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            
            TitlePanel(
                title = flashTitle,
                onTitleChange = { 
                    if (it.length <= 35) {
                        flashTitle = it 
                    }
                }
            )

            SectionWrapper(title = stringResource(localesR.string.duration), icon = Icons.Rounded.Timelapse) {
                DurationPanel(
                    duration = flashDuration,
                    isInfinite = infiniteDuration,
                    onConfirmDuration = { flashDuration = it },
                    onConfirmInfiniteDuration = { infiniteDuration = it }
                )
            }

            if(flashType == 0 || flashType == 1){
                SectionWrapper(title = stringResource(localesR.string.screen_flash), icon = TablerIcons.BrightnessUp) {
                    ScreenFlashPanel(
                        brightness = flashBrightness,
                        color = flashColor,
                        onConfirmBrightness = { flashBrightness = it },
                        onConfirmColor = { flashColor = it }
                    )
                }
            }

            if(flashType == 1 || flashType == 2){
                SectionWrapper(title = stringResource(localesR.string.rear_flash), icon = TablerIcons.Bolt) {
                    FlashPanel(
                        bpm = flashBpm,
                        intensity = intensity,
                        onConfirmBpm = { flashBpm = it },
                        onConfirmIntensity = { intensity = it }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        val lumoFlash = updateFlash?.copy(
                            title = flashTitle,
                            duration = flashDuration,
                            infiniteDuration = infiniteDuration,
                            screenColor = flashColor.toArgb().toHexColor(),
                            screenBrightness = flashBrightness.toFloat(),
                            flashBpmRate = flashBpm,
                            flashIntensity = intensity
                        )
                            ?: LumoFlash(
                                title = flashTitle,
                                flashType = flashType,
                                isPinned = false,
                                primaryOrderIndex = 0,
                                pinnedOrderIndex = 0,
                                duration = flashDuration,
                                infiniteDuration = infiniteDuration,
                                screenColor = flashColor.toArgb().toHexColor(),
                                screenBrightness = flashBrightness.toFloat(),
                                flashBpmRate = flashBpm,
                                flashIntensity = intensity
                            )

                        if (updateFlash == null) {
                            onCreate(lumoFlash)

                        } else {
                            onUpdate(lumoFlash)
                        }
                        onDismiss()
                    },
                    enabled = flashTitle.isNotBlank()
                ) {
                    Text(
                        text = if (updateFlash == null) stringResource(localesR.string.create_now) else stringResource(localesR.string.update_now),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionWrapper(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                letterSpacing = 1.sp
            )
        }
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun FlashDetailsBottomSheetPreview(){
    LumolightTheme {
        FlashDetailsBottomSheet(
            flashType = 1,
            onCreate = {},
            onUpdate = {},
            onDismiss = {}
        )
    }
}