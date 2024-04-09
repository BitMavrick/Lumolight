package com.bitmavrick.lumolight.ui.components.quickActions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun QuickSOSButton(){

    val _timerValue = 10
    var timerValue by remember { mutableIntStateOf(10) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    var buttonStatus by remember { mutableStateOf(ButtonStatus.NONE) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(buttonStatus == ButtonStatus.RUNNING){
            Text(text = "SOS starts in $timerValue seconds")
        }else if(buttonStatus == ButtonStatus.ACTIVE) {
            Text(text = "SOS Now Active")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                when(buttonStatus) {
                    ButtonStatus.NONE -> {
                        buttonStatus = ButtonStatus.RUNNING
                        timerJob = CoroutineScope(Dispatchers.Default).launch {
                            repeat(_timerValue){
                                delay(1000)
                                timerValue --
                            }

                            buttonStatus = ButtonStatus.ACTIVE
                            // TODO: Code for SOS activation
                        }
                    }

                    ButtonStatus.RUNNING -> {
                        timerJob?.cancel()
                        timerValue = _timerValue
                        buttonStatus = ButtonStatus.NONE
                    }

                    ButtonStatus.ACTIVE -> {
                        buttonStatus = ButtonStatus.NONE
                        timerValue = _timerValue
                    }
                }


            }
        ) {
            when(buttonStatus) {
                ButtonStatus.NONE -> {
                    Text(text = "Start")
                }

                ButtonStatus.RUNNING -> {
                    Text(text = "Cancel")
                }

                ButtonStatus.ACTIVE -> {
                    Text(text = "Stop")
                }
            }
        }

    }
}

private enum class ButtonStatus {
    NONE,
    RUNNING,
    ACTIVE
}