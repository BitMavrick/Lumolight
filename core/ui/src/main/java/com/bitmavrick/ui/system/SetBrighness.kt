package com.bitmavrick.ui.system

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun SetBrightness(level: Float) {
    val context = LocalContext.current
    Text("level: $level")
    DisposableEffect(Unit) {
        brightness(context, isFull = true, level)
        onDispose {
            brightness(context, isFull = false)
        }
    }
}

fun brightness(context: Context, isFull: Boolean, level: Float = 1f) {
    val activity = context as? Activity ?: return
    val layoutParams: WindowManager.LayoutParams = activity.window.attributes
    layoutParams.screenBrightness = if (isFull) level else WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE // ? 1f is the brightness value
    activity.window.attributes = layoutParams
}