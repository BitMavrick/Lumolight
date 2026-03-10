package com.bitmavrick.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bitmavrick.screen.ScreenFlashUiEvent
import com.bitmavrick.screen.ScreenFlashUiState
import com.bitmavrick.ui.details.FlashDetailsBottomSheet
import compose.icons.TablerIcons
import compose.icons.tablericons.BrightnessUp
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MainContent(
    uiState: ScreenFlashUiState,
    onEvent: (ScreenFlashUiEvent) -> Unit
) {
    if(uiState.isLoading && uiState.screenFlashList.isEmpty()){
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
    } else if(!uiState.isLoading && uiState.screenFlashList.isEmpty()){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Icon(
                    imageVector = TablerIcons.BrightnessUp,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp).padding(16.dp),
                )
            }
            item{
                Text(
                    text = stringResource(localesR.string.not_found),
                    style = MaterialTheme.typography.titleLarge,

                )
            }
            item{
                Text(
                    text = stringResource(localesR.string.screen_not_found_des),
                    textAlign = TextAlign.Center
                )
            }

            item {
                var openNewFlashSheet by remember { mutableStateOf(false) }

                OutlinedButton(
                    onClick = {
                        openNewFlashSheet = true
                    },
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(stringResource(localesR.string.create_now))
                }

                if(openNewFlashSheet){
                    FlashDetailsBottomSheet(
                        onCreate = {
                            onEvent(ScreenFlashUiEvent.AddNewFlash(it))
                            openNewFlashSheet = false
                        },
                        flashType = 0,
                        onDismiss = {
                            openNewFlashSheet = false
                        }
                    )
                }
            }
        }
    } else{
        LumoFlashGrid(
            flashList = uiState.screenFlashList,
            modifier = Modifier.fillMaxSize(),
            onEvent = onEvent
        )
    }
}