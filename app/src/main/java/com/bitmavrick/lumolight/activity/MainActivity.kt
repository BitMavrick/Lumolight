/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.activity.core.CoreViewModel
import com.bitmavrick.lumolight.activity.core.InitialLoadingScreen
import com.bitmavrick.lumolight.activity.core.Lumolight
import com.bitmavrick.lumolight.ui.screen.setting.Appearance
import com.bitmavrick.lumolight.ui.theme.LumolightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        setContent {
            val coreViewModel: CoreViewModel = viewModel()
            val coreUiState by coreViewModel.uiState.collectAsState()

            LumolightTheme(
                darkTheme = when(coreUiState.appearance) {
                    Appearance.DEFAULT -> isSystemInDarkTheme()
                    Appearance.LIGHT -> false
                    Appearance.DARK -> true
                },
                dynamicColor = coreUiState.dynamicTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isVisible by remember { mutableStateOf(false) }

                    LaunchedEffect(coreUiState.appLoading) {
                        isVisible = coreUiState.appLoading
                    }

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Lumolight()
                    }

                    AnimatedVisibility(
                        visible = !isVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        InitialLoadingScreen()
                    }
                }
            }
        }
    }
}


