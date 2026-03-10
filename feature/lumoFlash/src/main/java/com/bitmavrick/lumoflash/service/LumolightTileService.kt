package com.bitmavrick.lumoflash.service

import android.app.PendingIntent
import android.content.Intent
import android.service.quicksettings.TileService
import androidx.core.service.quicksettings.PendingIntentActivityWrapper
import androidx.core.service.quicksettings.TileServiceCompat
import com.bitmavrick.lumoflash.activity.LumoFlashActivity

class LumolightTileService : TileService() {
    override fun onClick() {
        super.onClick()

        Intent(applicationContext, LumoFlashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("flash_id", -4)
        }.also {
            TileServiceCompat.startActivityAndCollapse(
                this@LumolightTileService,
                PendingIntentActivityWrapper(
                    this@LumolightTileService,
                    0,
                    it,
                    PendingIntent.FLAG_UPDATE_CURRENT,
                    true
                )
            )
        }
    }
}