package com.bitmavrick.shortcuts.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bitmavrick.shortcuts.ShortcutUiEvent
import com.bitmavrick.shortcuts.ShortcutUiState
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PinnedFlashScreen(
    uiState: ShortcutUiState,
    onEvent: (ShortcutUiEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        if(uiState.userMessage != null){
            snackbarHostState.showSnackbar(uiState.userMessage)
            onEvent(ShortcutUiEvent.UserMessageShown)
        }
    }

    Scaffold(
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
    ) {
        PullToRefreshBox(
            modifier = Modifier.padding(it),
            isRefreshing = uiState.isLoading,
            onRefresh = { onEvent(ShortcutUiEvent.Refresh) }
        ) {
            MainContent(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PinnedFlashScreenPreview(){
    PinnedFlashScreen(
        uiState = ShortcutUiState(),
        onEvent = {}
    )
}