package com.bitmavrick.lumolight.ui.components.quickActions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.ui.screen.quickActions.QuickActionsViewModel
import com.bitmavrick.lumolight.ui.screen.quickActions.QuickSOSButtonStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuickSOSButton(
    viewModel: QuickActionsViewModel
){
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(uiState.quickSOSRunningSeconds != null && uiState.quickSOSButtonStatus == QuickSOSButtonStatus.RUNNING){
            Text(text = "SOS will starts in ${uiState.quickSOSRunningSeconds} seconds")
        }else if(uiState.quickSOSButtonStatus == QuickSOSButtonStatus.ACTIVE){
            Text(text = "SOS Now Active!")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                when(uiState.quickSOSButtonStatus) {
                    QuickSOSButtonStatus.NONE -> {
                        viewModel.runSOSTimer()
                    }

                    QuickSOSButtonStatus.RUNNING -> {
                        viewModel.cancelSOSTimer()
                    }

                    QuickSOSButtonStatus.ACTIVE -> {
                        viewModel.stopSOS()
                    }
                }


            }
        ) {
            when(uiState.quickSOSButtonStatus) {
                QuickSOSButtonStatus.NONE -> {
                    Text(text = "Start")
                }

                QuickSOSButtonStatus.RUNNING -> {
                    Text(text = "Cancel")
                }

                QuickSOSButtonStatus.ACTIVE -> {
                    Text(text = "Stop")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuickSOSButtonPreview(){
    QuickSOSButton(viewModel = QuickActionsViewModel())
}