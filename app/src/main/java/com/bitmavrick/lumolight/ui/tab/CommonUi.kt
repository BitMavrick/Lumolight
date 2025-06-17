/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.bitmavrick.lumolight.util.ColorValue
import com.bitmavrick.lumolight.util.vibrate

@Composable
fun CustomOutlinedButton(
    buttonText : String,
    onClick: () -> Unit,
    color: Color = Color.Red,
    hapticStatus: Boolean = false
) {
    val customShape = RoundedCornerShape(percent = 15)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 2.dp,
                color = color,
                shape = customShape
            )
            .clickable {
                onClick()
                if (hapticStatus) {
                    vibrate(context)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText.uppercase(),
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CustomFilledButton(
    colorIndex: Int? = null,
    buttonText: String,
    onClick: () -> Unit,
    hapticStatus: Boolean = false
) {
    val customShape = RoundedCornerShape(percent = 15)
    val context = LocalContext.current

    val targetColor = if (colorIndex != null && colorIndex in ColorValue.list.indices) {
        Color(ColorValue.list[colorIndex].code.toColorInt())
    } else {
        MaterialTheme.colorScheme.primary
    }

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 300),
        label = "buttonColorAnimation"
    )

    Surface(
        shape = customShape,
        color = animatedColor,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                onClick()
                if (hapticStatus) {
                    vibrate(context)
                }
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText.uppercase(),
                color = if (colorIndex == 0) Color.Black else MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomOutlinedButtonPreview(){
    CustomOutlinedButton(
        buttonText = "Outlined Button",
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CustomFilledButtonPreview(){
    CustomFilledButton(
        buttonText = "Filled Button",
        onClick = {}
    )
}