package com.bitmavrick.lumolight.service

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

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
        qsTile.label = if(active){ "Flash On" }else{ "Flash Off" }
        qsTile.updateTile()

        Log.e("Tile", " ------>> Tile is clicked.")
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        Log.e("Tile", " ------>> Tile is removed.")
    }
}