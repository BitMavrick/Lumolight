package com.bitmavrick.shortcuts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bitmavrick.shortcuts.ShortcutUiEvent
import com.bitmavrick.shortcuts.ShortcutUiState
import com.bitmavrick.locales.R as localesR

@Composable
fun MainContent(
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
                Text(stringResource(localesR.string.loading))
            }
        }
    } else if(!uiState.isLoading && uiState.pinnedFlashList.isEmpty()){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                Text(stringResource(localesR.string.not_found))
            }
        }
    } else{
        LumoFlashGrid(
            flashList = uiState.pinnedFlashList,
            modifier = Modifier.fillMaxSize(),
            onEvent = onEvent
        )
    }
}