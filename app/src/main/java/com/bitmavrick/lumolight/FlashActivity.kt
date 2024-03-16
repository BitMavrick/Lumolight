package com.bitmavrick.lumolight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.bitmavrick.lumolight.ui.theme.LumoLightTheme

class FlashActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            LumoLightTheme {
                Text(text = "This is the flash activity")
            }
        }
    }
}