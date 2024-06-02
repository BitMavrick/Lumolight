/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.service

import android.app.PendingIntent
import android.content.Intent
import android.service.quicksettings.TileService
import androidx.core.service.quicksettings.PendingIntentActivityWrapper
import androidx.core.service.quicksettings.TileServiceCompat
import com.bitmavrick.lumolight.activity.FlashTileActivity

class LumolightTileService : TileService() {

    override fun onClick() {
        super.onClick()

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