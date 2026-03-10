package com.bitmavrick.shortcuts.components

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bitmavrick.lumoflash.activity.LumoFlashActivity
import com.bitmavrick.shortcuts.BuildConfig
import com.bitmavrick.shortcuts.ShortcutUiEvent
import com.bitmavrick.shortcuts.ShortcutUiState
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShortcutScreen(
    uiState: ShortcutUiState,
    onEvent: (ShortcutUiEvent) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(localesR.string.home),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { innerPadding ->

        LocalContext.current
        val options = listOf(
            stringResource(localesR.string.screen),
            stringResource(localesR.string.dual),
            stringResource(localesR.string.rear)
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /*
                    if(!uiState.lumolightPremium){
                        if(ReleaseMode.STATUS == ReleaseMode.PRODUCTION){
                            BannerAd(
                                forceTestAd = false
                            )
                        }else{
                            BannerAd(
                                forceTestAd = true
                            )
                        }
                    }
                     */
                }

                Spacer(Modifier.height(68.dp))

                Row (
                    Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    QuickStartButton(
                        onClickStart = {
                            when(uiState.quickButtonIndex){
                                0 -> {
                                    val intent = Intent(context, LumoFlashActivity::class.java).apply {
                                        putExtra("flash_id", -1)
                                    }
                                    context.startActivity(intent)
                                }
                                1 -> {
                                    val intent = Intent(context, LumoFlashActivity::class.java).apply {
                                        putExtra("flash_id", -2)
                                    }
                                    context.startActivity(intent)
                                }
                                2 -> {
                                    val intent = Intent(context, LumoFlashActivity::class.java).apply {
                                        putExtra("flash_id", -3)
                                    }
                                    context.startActivity(intent)
                                }
                            }
                        }
                    )
                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f).padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                ) {
                    val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1.5f), Modifier.weight(1f))

                    options.forEachIndexed { index, label ->
                        ToggleButton(
                            checked = index == uiState.quickButtonIndex,
                            onCheckedChange = {
                                onEvent(ShortcutUiEvent.UpdateQuickButtonIndex(index))
                            },
                            modifier = modifiers[index].semantics { role = Role.RadioButton },
                            shapes =
                                when (index) {
                                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                    options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                }
                        ) {
                            Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                            Text(
                                text = label,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ShortcutScreenPreview(){
    LumolightTheme {
        ShortcutScreen(
            uiState = ShortcutUiState(),
            onEvent = {}
        )
    }
}