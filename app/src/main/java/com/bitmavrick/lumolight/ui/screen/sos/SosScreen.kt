package com.bitmavrick.lumolight.ui.screen.sos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SosScreen(
    onExitClick: () -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onExitClick() }
        ) {
            Text(text = "Exit SOS Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SosScreenPreview() {
    SosScreen(
        onExitClick = {}
    )
}