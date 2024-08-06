/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright © BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.activity.core.CoreViewModel
import com.bitmavrick.lumolight.activity.core.Lumolight
import com.bitmavrick.lumolight.ui.screen.setting.Appearance
import com.bitmavrick.lumolight.ui.theme.LumolightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var splashScreen: SplashScreen
    private lateinit var coreViewModel: CoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        setContent {
            coreViewModel = viewModel()
            val coreUiState by coreViewModel.uiState.collectAsState()

            splashScreen.setKeepOnScreenCondition {
                coreUiState.appLoadingStatus
            }

            LumolightTheme(
                darkTheme = when(coreUiState.appearance) {
                    Appearance.DEFAULT -> isSystemInDarkTheme()
                    Appearance.LIGHT -> false
                    Appearance.DARK -> true
                },
                dynamicColor = coreUiState.dynamicTheme,
                darkOled = coreUiState.oledTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Lumolight()
                }
            }
        }
    }
}


