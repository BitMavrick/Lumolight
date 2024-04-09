package com.bitmavrick.lumolight.ui.components.quickActions

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview()
@Composable
fun QuickStartButton(){
    var selectedIndex by remember { mutableStateOf(false) }
    val backgroundButtonColor: CardColors
    val foregroundButtonColor: CardColors

    if (selectedIndex){
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
        modifier = Modifier.aspectRatio(1.0f).padding(8.dp)
            .noRippleClickable(
                onClick = { selectedIndex = !selectedIndex }
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
                    if (selectedIndex){
                        Text(
                            text = "STOP",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }else{
                        Text(
                            text = "START",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
