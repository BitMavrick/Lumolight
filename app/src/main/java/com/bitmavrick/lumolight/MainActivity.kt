package com.bitmavrick.lumolight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.navigation.Lumolight
import com.bitmavrick.settings.SettingsViewModel
import com.bitmavrick.theme.LumolightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var splashScreen: SplashScreen
    private lateinit var settingsViewModel: SettingsViewModel

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            settingsViewModel = viewModel()
            val settingsUiState by settingsViewModel.uiState.collectAsState()

            splashScreen.setKeepOnScreenCondition {
                !settingsUiState.isLoaded
            }

            LumolightTheme(
                darkTheme = intToTheme(settingsUiState.darkTheme),
                dynamicColor = settingsUiState.dynamicTheme,
                amoledMode = settingsUiState.amoledTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Lumolight(
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun intToTheme(value: Int) : Boolean {
    when (value) {
        0 -> return isSystemInDarkTheme()
        1 -> return false
        2 -> return true
    }
    return isSystemInDarkTheme()
}
