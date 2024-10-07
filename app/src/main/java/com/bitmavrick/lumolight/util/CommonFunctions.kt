/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

fun openUrl(context: Context, url: String){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

