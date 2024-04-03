package com.bitmavrick.lumolight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.bitmavrick.lumolight.ui.LumolightApp
import com.bitmavrick.lumolight.ui.theme.LumolightTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LumolightTheme {
                Surface{
                    val windowSize = calculateWindowSizeClass(this)

                    LumolightApp(
                        windowSize = windowSize.widthSizeClass
                    )
                }
            }
        }
    }
}