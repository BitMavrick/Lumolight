package com.bitmavrick.ui.system

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getMaxFlashlightStrengthValue(context: Context) : Int {
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)

    val torchMaxLevel = cameraCharacteristics[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL] ?: 1

    return torchMaxLevel
}