package com.bitmavrick.ui.system

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay

suspend fun activateFlashlight(
    active: Boolean,
    context: Context,
    bpm : Int = 0,
) {
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    if(bpm == 0){
        cameraManager.setTorchMode(cameraId, active)
    }else{
        val bpm = 60000L / bpm.toLong()
        while(true){
            if(!active) break
            cameraManager.setTorchMode(cameraId, true)
            delay(bpm / 2)
            cameraManager.setTorchMode(cameraId, false)
            delay(bpm / 2)
        }
    }
}

suspend fun activateFlashlightWithIntensity(
    active: Boolean,
    context: Context,
    bpm : Int = 0,
    intensity: Int = 0,
    maxIntensity: Int = 0
) {
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    if(bpm == 0){
        if (maxIntensity > 1) {
            if(active){
                cameraManager.turnOnTorchWithStrengthLevel(cameraId, intensity)
            }else{
                cameraManager.setTorchMode(cameraId, false)
            }
        }else{
            cameraManager.setTorchMode(cameraId, active)
        }
    }else{
        val bpm = 60000L / bpm.toLong()
        while(true){
            if(!active) break

            if (maxIntensity > 1) {
                cameraManager.turnOnTorchWithStrengthLevel(cameraId, intensity)
            }else{
                cameraManager.setTorchMode(cameraId, true)
            }

            delay(bpm / 2)
            cameraManager.setTorchMode(cameraId, false)
            delay(bpm / 2)
        }
    }
}

fun deactivateFlashlight(
    context: Context
){
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    cameraManager.setTorchMode(cameraId, false)
}