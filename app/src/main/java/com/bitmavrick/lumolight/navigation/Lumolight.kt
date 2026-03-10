package com.bitmavrick.lumolight.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.bitmavrick.billing.BillingManager
import com.bitmavrick.both.BothFlashScreen
import com.bitmavrick.both.BothFlashViewModel
import com.bitmavrick.flash.FlashlightScreen
import com.bitmavrick.flash.FlashlightViewModel
import com.bitmavrick.screen.ScreenFlashViewModel
import com.bitmavrick.screen.ScreenScreen
import com.bitmavrick.settings.SettingsScreen
import com.bitmavrick.settings.SettingsViewModel
import com.bitmavrick.shortcuts.HomeScreen
import com.bitmavrick.shortcuts.ShortcutViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lumolight(
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.SHORTCUTS) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        val icon = if (it == currentDestination) {
                            it.selectedIcon
                        } else {
                            it.icon
                        }

                        when (icon) {
                            is AppIcon.Vector -> {
                                Icon(
                                    imageVector = icon.imageVector,
                                    contentDescription = it.contentDescription
                                )
                            }
                            is AppIcon.Drawable -> {
                                // Now we are inside a Composable, so we can safely call painterResource
                                Icon(
                                    painter = painterResource(id = icon.id),
                                    contentDescription = it.contentDescription
                                )
                            }
                        }
                    },
                    label = { Text(text = stringResource(it.label)) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ){
        val shortcutViewModel: ShortcutViewModel = hiltViewModel()
        val screenFlashViewModel: ScreenFlashViewModel = hiltViewModel()
        val flashlightViewModel: FlashlightViewModel = hiltViewModel()
        val bothFlashViewModel: BothFlashViewModel = hiltViewModel()

        LaunchedEffect(Unit) {
            delay(200)

            BillingManager(context){ isPurchased ->
                if(isPurchased){
                    settingsViewModel.updateLumolightPremium(true)
                }else{
                    settingsViewModel.updateLumolightPremium(false)
                }
            }
        }

        when(currentDestination){
            AppDestinations.SHORTCUTS -> {
                val shortcutUiState by shortcutViewModel.uiState.collectAsState()
                HomeScreen(
                    uiState = shortcutUiState,
                    onEvent = shortcutViewModel::onEvent
                )
            }
            AppDestinations.SCREEN -> {
                val screenFlashUiState by screenFlashViewModel.uiState.collectAsState()
                ScreenScreen(
                    uiState = screenFlashUiState,
                    onEvent = screenFlashViewModel::onEvent
                )
            }
            AppDestinations.FLASH -> {
                val flashlightUiState by flashlightViewModel.uiState.collectAsState()
                FlashlightScreen(
                    uiState = flashlightUiState,
                    onEvent = flashlightViewModel::onEvent
                )
            }
            AppDestinations.BOTH -> {
                val bothFlashUiState by bothFlashViewModel.uiState.collectAsState()
                BothFlashScreen(
                    uiState = bothFlashUiState,
                    onEvent = bothFlashViewModel::onEvent
                )
            }
            AppDestinations.SETTINGS -> {
                val settingsUiState by settingsViewModel.uiState.collectAsState()
                SettingsScreen(
                    uiState = settingsUiState,
                    onEvent = settingsViewModel::onEvent
                )
            }
        }
    }
}

