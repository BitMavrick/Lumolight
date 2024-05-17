package com.bitmavrick.lumolight.service

import android.app.PendingIntent
import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.core.service.quicksettings.PendingIntentActivityWrapper
import androidx.core.service.quicksettings.TileServiceCompat
import com.bitmavrick.lumolight.activity.FlashTileActivity

class LumolightTileService : TileService() {
    private var active = false

    override fun onClick() {
        super.onClick()

        qsTile.state = if(!active) {
            Tile.STATE_ACTIVE
        }else {
            Tile.STATE_INACTIVE
        }
        active = !active
        qsTile.label = if(active){ "Flash On" }else{ "Lumolight" }

        qsTile.updateTile()

        if(active){
            Intent(applicationContext, FlashTileActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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
}