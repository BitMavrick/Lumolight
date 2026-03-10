package com.bitmavrick.both

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bitmavrick.ui.details.FlashDetailsBottomSheet
import com.bitmavrick.both.components.MainContent
import com.bitmavrick.lumoflash.activity.LumoFlashActivity
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BothFlashScreen(
    uiState: BothFlashUiState,
    onEvent: (BothFlashUiEvent) -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var openNewFlashSheet by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.userMessage) {
        if(uiState.userMessage != null){
            snackbarHostState.showSnackbar(uiState.userMessage)
            onEvent(BothFlashUiEvent.UserMessageShown)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(localesR.string.dual_flash),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },

                actions = {
                    FilledTonalIconButton(
                        onClick = {
                            val intent = Intent(context, LumoFlashActivity::class.java).apply {
                                putExtra("flash_id", -2)
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
                            openNewFlashSheet = true
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
            onRefresh = { onEvent(BothFlashUiEvent.Refresh) }
        ) {
            MainContent(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }


    if(openNewFlashSheet){
        FlashDetailsBottomSheet(
            onCreate = {
                onEvent(BothFlashUiEvent.AddNewFlash(it))
            },
            flashType = 1,
            onDismiss = {
                openNewFlashSheet = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BothFlashScreenPreview(){
    LumolightTheme {
        BothFlashScreen(
            uiState = BothFlashUiState(),
            onEvent = {}
        )
    }
}