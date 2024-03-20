package com.bitmavrick.lumolight.service

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.bitmavrick.lumolight.FlashActivity

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

    var active = false


    override fun onClick() {
        super.onClick()
        qsTile.state = if(!active) {
            Tile.STATE_ACTIVE
        }else {
            Tile.STATE_INACTIVE
        }
        active = !active
        qsTile.label = if(active){ "Light On" }else{ "Lumo Light" }

        // Successfully open the flash activity in android 12
        if(active) {
            val intent = Intent(applicationContext, FlashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        qsTile.updateTile()

        Log.e("Tile", " ------>> Tile is clicked.")
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        Log.e("Tile", " ------>> Tile is removed.")
    }
}