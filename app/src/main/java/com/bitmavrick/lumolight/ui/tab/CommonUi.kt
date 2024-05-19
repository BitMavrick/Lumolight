package com.bitmavrick.lumolight.ui.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedButton(
    buttonText : String,
    onClick: () -> Unit,
    color: Color = Color.Red
) {
    val customShape = RoundedCornerShape(percent = 15)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 2.dp,
                color = color,
                shape = customShape
            )
            .clickable { onClick() },
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
    buttonText : String,
    onClick: () -> Unit
) {
    val customShape = RoundedCornerShape(percent = 15)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = customShape
            )
            .clickable{ onClick() },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = buttonText.uppercase(),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
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