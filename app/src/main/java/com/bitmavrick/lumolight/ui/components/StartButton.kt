package com.bitmavrick.lumolight.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
fun StartButton(){
    var selectedIndex by remember { mutableStateOf(true) }
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
            containerColor = Color.DarkGray
        )

        foregroundButtonColor = CardDefaults.cardColors(
            containerColor = Color.Green,
            contentColor = Color.DarkGray
        )
    }

    Card(
        modifier = Modifier.aspectRatio(1.0f)
            .noRippleClickable(
                onClick = { selectedIndex = !selectedIndex }
            ),
        shape = CircleShape,
        colors = backgroundButtonColor,
        elevation = CardDefaults.cardElevation()

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
                        Text(text = "STOP")
                    }else{
                        Text(text = "START")
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
