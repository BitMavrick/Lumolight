/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.activity

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.system.KeepScreenOn
import com.bitmavrick.lumolight.ui.tab.CustomOutlinedButton
import kotlin.system.exitProcess

class FlashTileActivity: ComponentActivity() {

    private var brightnessLevel = mutableFloatStateOf(0.4f)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        changeBrightness(0f)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            TileFlash()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                changeBrightness(0.1f)
                true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                changeBrightness(-0.1f)
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    private fun changeBrightness(change: Float) {
        brightnessLevel.floatValue = (brightnessLevel.floatValue + change).coerceIn(0.0f, 1.0f)
        val layoutParams = window.attributes
        layoutParams.screenBrightness = brightnessLevel.floatValue
        window.attributes = layoutParams
    }
}


@Preview(showBackground = true)
@Composable
fun VolumeInfo(){
    val context = LocalContext.current

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = context.getString(R.string.brightness_up_icon_description),
            tint = androidx.compose.ui.graphics.Color.Gray
        )

        Text(
            text = context.getString(R.string.brightness_suggestion),
            modifier = Modifier.padding(vertical = 12.dp),
            color = androidx.compose.ui.graphics.Color.Gray
        )

        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = context.getString(R.string.brightness_down_icon_description),
            tint = androidx.compose.ui.graphics.Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TileFlash() {
    KeepScreenOn()

    Scaffold (
        content = { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize().padding(innerPadding)
                    .background(androidx.compose.ui.graphics.Color.White).padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(30.dp))
                Column (
                    Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    VolumeInfo()
                }
                CustomOutlinedButton(
                    buttonText = "Exit",
                    onClick = {
                        exitProcess(0)
                    }
                )
            }
        }
    )
}