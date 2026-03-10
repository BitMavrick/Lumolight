package com.bitmavrick.screen

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bitmavrick.lumoflash.activity.LumoFlashActivity
import com.bitmavrick.screen.components.MainContent
import com.bitmavrick.ui.details.FlashDetailsBottomSheet
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScreenScreen(
    uiState: ScreenFlashUiState,
    onEvent: (ScreenFlashUiEvent) -> Unit
){
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val openNewFlashSheet = remember { mutableStateOf(false) }

    LaunchedEffect(uiState.userMessage) {
        if(uiState.userMessage != null){
            snackbarHostState.showSnackbar(uiState.userMessage)
            onEvent(ScreenFlashUiEvent.UserMessageShown)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(localesR.string.screen_flash),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },

                actions = {
                    FilledTonalIconButton(
                        onClick = {
                            val intent = Intent(context, LumoFlashActivity::class.java).apply {
                                putExtra("flash_id", -1)
                            }
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PowerSettingsNew,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            openNewFlashSheet.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        PullToRefreshBox(
            modifier = Modifier.padding(it),
            isRefreshing = uiState.isLoading,
            onRefresh = { onEvent(ScreenFlashUiEvent.Refresh) }
        ) {
            MainContent(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }

    if(openNewFlashSheet.value){
        FlashDetailsBottomSheet(
            onCreate = {
                onEvent(ScreenFlashUiEvent.AddNewFlash(it))
            },
            flashType = 0,
            onDismiss = {
                openNewFlashSheet.value = false
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ScreenScreenPreview(){
    ScreenScreen(
        uiState = ScreenFlashUiState(),
        onEvent = {}
    )
}