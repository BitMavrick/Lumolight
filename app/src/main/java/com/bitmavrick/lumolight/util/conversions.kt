package com.bitmavrick.lumolight.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import com.bitmavrick.lumolight.ui.screen.setting.Appearance

@SuppressLint("DefaultLocale")
fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d : %02d", minutes, remainingSeconds)
}

fun getAppVersion(context: Context): String {
    return if(AppConstants.APP_PRODUCTION_MODE == ProductionMode.RELEASE){
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "Unable to fetch"
        }
    }else {
        "Unknown" // Due to the preview limitation we had to do this!
    }
}

fun String.toAppearance(): Appearance {
    return try {
        Appearance.valueOf(this.uppercase())
    } catch (e: IllegalArgumentException) {
        Appearance.DEFAULT // Fallback to DEFAULT if the string doesn't match any enum
    }
}