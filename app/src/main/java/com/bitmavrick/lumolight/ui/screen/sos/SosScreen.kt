package com.bitmavrick.lumolight.ui.screen.sos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SosScreen(
    sosViewModel: SosViewModel,
    onExitClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                sosViewModel.toggleFlashLight(context, false)
                sosViewModel.updateFlashlightStatus(false)
                onExitClick()
            }
        ) {
            Text(text = "Exit SOS Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SosScreenPreview() {
    val sosViewModel : SosViewModel = viewModel()
    SosScreen(
        sosViewModel = sosViewModel,
        onExitClick = {}
    )
}