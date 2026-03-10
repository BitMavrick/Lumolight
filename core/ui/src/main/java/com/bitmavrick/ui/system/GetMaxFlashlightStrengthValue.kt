package com.bitmavrick.ui.system

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getMaxFlashlightStrengthValue(context: Context) : Int {
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) ?: return 1
    val cameraId = getFlashCameraId(context) ?: return 1
    
    try {
        val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
        return cameraCharacteristics[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL] ?: 1
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return 1
}