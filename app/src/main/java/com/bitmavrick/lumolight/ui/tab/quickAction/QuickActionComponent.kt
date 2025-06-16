/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.quickAction

import android.annotation.SuppressLint
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
) {
    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)

    // Animate background and foreground colors
    val backgroundColor by animateColorAsState(
        targetValue = if (uiState.startButtonStatus) Color.White
        else MaterialTheme.colorScheme.primaryContainer,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val foregroundContainerColor by animateColorAsState(
        targetValue = if (uiState.startButtonStatus) Color.Red
        else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val foregroundContentColor by animateColorAsState(
        targetValue = if (uiState.startButtonStatus) Color.White
        else MaterialTheme.colorScheme.onPrimary,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val backgroundButtonColor = CardDefaults.cardColors(
        containerColor = backgroundColor
    )

    val foregroundButtonColor = CardDefaults.cardColors(
        containerColor = foregroundContainerColor,
        contentColor = foregroundContentColor
    )

    Card(
        modifier = Modifier
            .aspectRatio(1.0f)
            .padding(8.dp)
            .noRippleClickable(
                onClick = {
                    if (uiState.hapticStatus) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            vibrator.vibrate(
                                VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
                            )
                        }
                    }
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
        ) {
            Card(
                modifier = Modifier.padding(25.dp),
                shape = CircleShape,
                colors = foregroundButtonColor
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (uiState.startButtonLittleLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    } else {
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
