package com.bitmavrick.ui.system

import android.content.Context
import android.hardware.camera2.CameraManager
import kotlinx.coroutines.delay

suspend fun activateFlashlight(
    active: Boolean,
    context: Context,
    bpm : Int = 0,
) {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager  //ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager

    try{
        val cameraId = cameraManager.cameraIdList[0] //getFlashCameraId(context) ?: return

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
    } catch (e: Exception) {
        // Toast.makeText(context, "Error activating flashlight: ${e.message}", Toast.LENGTH_SHORT).show()
        println("Error activating flashlight: ${e.message}")
    }
}

suspend fun activateFlashlightWithIntensity(
    active: Boolean,
    context: Context,
    bpm : Int = 0,
    intensity: Int = 0,
    maxIntensity: Int = 0
) {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager // ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager

    try{
        val cameraId = cameraManager.cameraIdList[0] // getFlashCameraId(context) ?: return
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
    } catch (e: Exception) {
        // Toast.makeText(context, "Error activating flashlight: ${e.message}", Toast.LENGTH_SHORT).show()
        // Log.d("LUMO_FLASH", "${e.message}")
        println("Error activating flashlight: ${e.message}")
    }
}

fun deactivateFlashlight(
    context: Context
){
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager // ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager

    try {
        val cameraId = cameraManager.cameraIdList[0] // getFlashCameraId(context) ?: return
        cameraManager.setTorchMode(cameraId, false)
    } catch (e: Exception) {
        // Toast.makeText(context, "Error deactivating flashlight: ${e.message}", Toast.LENGTH_SHORT).show()
        println("Error deactivating flashlight: ${e.message}")
    }
}
