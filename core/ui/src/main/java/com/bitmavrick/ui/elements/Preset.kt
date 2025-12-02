package com.bitmavrick.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = false)
@Composable
fun Preset(
    modifier: Modifier = Modifier,
    textTint: Color = MaterialTheme.colorScheme.onSurface,
    title: String = "Title",
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
){
    OutlinedCard(
        onClick = {
            onClick()
        },
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(1.5.dp, if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer),
    ) {
        Row (
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        offset = Offset(1f, 1f),
                        blurRadius = 2f
                    )
                ),
                color = textTint,
            )
        }
    }
}
