package com.bitmavrick.lumolight.service

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.input.key.Key.Companion.O
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


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onClick() {
        super.onClick()
        qsTile.state = if(!active) {
            Tile.STATE_ACTIVE
        }else {
            Tile.STATE_INACTIVE
        }
        active = !active
        qsTile.label = if(active){ "Flash On" }else{ "Flash Off" }
        if(active){
            val intent = Intent(applicationContext, FlashActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            startActivityAndCollapse(pendingIntent)
        }
        qsTile.updateTile()

        Log.e("Tile", " ------>> Tile is clicked.")
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        Log.e("Tile", " ------>> Tile is removed.")
    }
}