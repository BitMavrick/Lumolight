package com.bitmavrick.ui.system

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import androidx.core.content.ContextCompat
import com.bitmavrick.locales.ReleaseMode

/**
 * Checks if the device has a physical flashlight (torch).
 */
fun hasFlashlight(context: Context): Boolean {
    return if(ReleaseMode.STATUS == ReleaseMode.DEBUG){
        true
    }else{
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }
}

/**
 * Returns the first camera ID that supports flash, or null if none found.
 */
fun getFlashCameraId(context: Context): String? {
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) ?: return null
    try {
        for (cameraId in cameraManager.cameraIdList) {
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false
            if (hasFlash) {
                return cameraId
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
