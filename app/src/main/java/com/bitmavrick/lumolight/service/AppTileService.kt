package com.bitmavrick.lumolight.service

import android.app.PendingIntent
import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.bitmavrick.lumolight.FlashActivity
import androidx.core.service.quicksettings.PendingIntentActivityWrapper
import androidx.core.service.quicksettings.TileServiceCompat

class AppTileService : TileService() {

    override fun onTileAdded() {
        super.onTileAdded()
        Log.e("Tile", " ------>> The tile added.")
    }

    override fun onStartListening() {
        super.onStartListening()
        Log.e("Tile", " ------>> The tile is updating.")
    }

    override fun onStopListening() {
        super.onStopListening()
        Log.e("Tile", " ------>> The tile is now stop updating.")
    }

    private var active = false

    override fun onClick() {
        super.onClick()

        qsTile.state = if(!active) {
            Tile.STATE_ACTIVE
        }else {
            Tile.STATE_INACTIVE
        }
        active = !active
        qsTile.label = if(active){ "Light On" }else{ "Lumo Light" }

        qsTile.updateTile()

        // Successfully open the flash activity in android 12
        if(active){
            Intent(applicationContext, FlashActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                TileServiceCompat.startActivityAndCollapse(
                    this@AppTileService,
                    PendingIntentActivityWrapper(
                        this@AppTileService,
                        0,
                        it,
                        PendingIntent.FLAG_UPDATE_CURRENT,
                        true
                    )
                )
            }
        }
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        Log.e("Tile", " ------>> Tile is removed.")
    }
}