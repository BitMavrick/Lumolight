package com.bitmavrick.lumolight.util

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d : %02d", minutes, remainingSeconds)
}