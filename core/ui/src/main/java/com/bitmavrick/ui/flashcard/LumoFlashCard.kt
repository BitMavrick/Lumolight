package com.bitmavrick.ui.flashcard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bitmavrick.data.domain.model.LumoFlash
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.ui.components.LumoTag
import com.bitmavrick.ui.system.hasFlashlight
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.BrightnessUp
import compose.icons.tablericons.Home
import com.bitmavrick.ui.R as IconR
import com.bitmavrick.locales.R as localesR

@SuppressLint("DefaultLocale")
@Composable
fun LumoFlashCard(
    flash: LumoFlash,
    onPower: () -> Unit,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    onAddToHome: () -> Unit = {},
    @SuppressLint("ModifierParameter") dragHandleModifier: Modifier = Modifier
) {
    val context = LocalContext.current

    OutlinedCard (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    when (flash.flashType) {
                        0 -> {
                            Icon(
                                imageVector = TablerIcons.BrightnessUp,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        1 -> {
                            Icon(
                                painter = painterResource(id = IconR.drawable.icon_both_flash),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        2 -> {
                            Icon(
                                imageVector = TablerIcons.Bolt,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = flash.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    LazyRow(
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    ) {
                        item {
                            LumoTag(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                icon = Icons.Default.Timelapse,
                                text = if(flash.infiniteDuration) "INFINITE" else String.format("%02d:%02d min", flash.duration / 60, flash.duration % 60),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }

                        if(flash.flashType == 0 || flash.flashType == 1){
                            item {
                                LumoTag(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    icon = TablerIcons.BrightnessUp,
                                    text = flash.screenColor + " • " + flash.screenBrightness?.toInt().toString() + "%",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        }

                        if(flash.flashType == 1 || flash.flashType == 2 && hasFlashlight(context)){
                            item {
                                LumoTag(
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    icon = TablerIcons.Bolt,
                                    text = flash.flashBpmRate.toString() + " BPM • Level " + flash.flashIntensity,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        }
                    }
                }

                FilledTonalIconButton(
                    onClick = {},
                    modifier = dragHandleModifier
                ) {
                    Icon(
                        imageVector = Icons.Default.DragIndicator,
                        contentDescription = null
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth().padding( top = 16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    onClick = onPower
                ) {
                    Icon(
                        imageVector = Icons.Default.PowerSettingsNew,
                        contentDescription = null
                    )
                }

                Box {
                    var expanded by remember { mutableStateOf(false) }

                    FilledTonalIconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(localesR.string.edit)) },
                            onClick = {
                                expanded = false
                                onEdit()
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Edit, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(localesR.string.delete)) },
                            onClick = {
                                expanded = false
                                onDelete()
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Delete, contentDescription = null)
                            }
                        )

                        DropdownMenuItem(
                            text = { Text(if(flash.isPinned ) stringResource(localesR.string.remove_from_home) else stringResource(localesR.string.add_to_home)) },
                            onClick = {
                                expanded = false
                                onAddToHome()
                            },
                            leadingIcon = {
                                Icon(TablerIcons.Home, contentDescription = null)
                            }
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LumoFlashCardPreview(){
    LumolightTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            val flash = LumoFlash(
                title = "This is a very long flash card title that should be ellipsized",
                flashType = 1,
                isPinned = false,
                duration = 121,
                infiniteDuration = false,
                screenColor = "#FFFFFF",
                screenBrightness = 10f,
                flashBpmRate = 0,
                flashIntensity = 1,
                pinnedOrderIndex = null
            )

            LumoFlashCard(
                flash = flash,
                onPower = {}
            )
            
            Spacer(modifier = Modifier.padding(8.dp))

            val flashShort = LumoFlash(
                title = "Short title",
                flashType = 0,
                isPinned = false,
                duration = 60,
                infiniteDuration = false,
                screenColor = "#FFFFFF",
                screenBrightness = 10f,
                flashBpmRate = 0,
                flashIntensity = 1,
                pinnedOrderIndex = null
            )

            LumoFlashCard(
                flash = flashShort,
                onPower = {}
            )
        }
    }
}