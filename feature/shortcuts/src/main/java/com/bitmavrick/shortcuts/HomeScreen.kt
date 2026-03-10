package com.bitmavrick.shortcuts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bitmavrick.shortcuts.components.PinnedFlashScreen
import com.bitmavrick.shortcuts.components.ShortcutScreen
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    uiState: ShortcutUiState,
    onEvent: (ShortcutUiEvent) -> Unit
) {
    if(uiState.isLoading && uiState.pinnedFlashList.isEmpty()){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                LoadingIndicator(
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    } else if(!uiState.isLoading && uiState.pinnedFlashList.isEmpty()){
        ShortcutScreen(
            uiState = uiState,
            onEvent = onEvent
        )
    } else {
        PinnedFlashScreen(
            uiState = uiState,
            onEvent = onEvent
        )
    }
}