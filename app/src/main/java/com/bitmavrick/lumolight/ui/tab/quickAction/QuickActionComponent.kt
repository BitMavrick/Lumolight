/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.quickAction

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.R

@Composable
fun QuickStartButton(
    uiState: QuickActionUiState,
    onClick: () -> Unit
){
    val backgroundButtonColor: CardColors
    val foregroundButtonColor: CardColors
    val context = LocalContext.current

    if (uiState.startButtonStatus){
        backgroundButtonColor = CardDefaults.cardColors(
            containerColor = Color.White
        )

        foregroundButtonColor = CardDefaults.cardColors(
            containerColor = Color.Red,
            contentColor = Color.White
        )
    }else{
        backgroundButtonColor = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )

        foregroundButtonColor = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    }

    Card(
        modifier = Modifier
            .aspectRatio(1.0f)
            .padding(8.dp)
            .noRippleClickable(
                onClick = {
                    onClick()
                }
            ),
        shape = CircleShape,
        colors = backgroundButtonColor,
        elevation = CardDefaults.cardElevation(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer)

    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Card(
                modifier = Modifier.padding(30.dp),
                shape = CircleShape,
                colors = foregroundButtonColor
            ){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    if(uiState.startButtonLittleLoading){
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    }else{
                        Icon(
                            imageVector = Icons.Filled.PowerSettingsNew,
                            contentDescription = context.getString(R.string.power_icon_description),
                            modifier = Modifier.size(62.dp)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        enabled = true,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Preview(showBackground = true)
@Composable
fun QuickStartButtonPreview(){
    QuickStartButton(
        uiState = QuickActionUiState(),
        onClick = {}
    )
}
