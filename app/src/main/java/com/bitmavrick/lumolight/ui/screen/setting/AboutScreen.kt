package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bitmavrick.lumolight.ui.theme.LumolightTheme

@Composable
fun AboutScreen(
    settingUiState: SettingUiState,
    settingOnEvent: (SettingUiEvent) -> Unit,
    onClickBack: () -> Unit
) {
    Text(text = "This is about screen")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    LumolightTheme {
        AboutScreen(
            settingUiState = SettingUiState(),
            settingOnEvent = {},
            onClickBack = {}
        )
    }
}