package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickActionScreen(
    viewModel: QuickActionViewModel
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value
    val options = listOf("Screen", "Both", "Flash")

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(2f),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // BannerAd()
                Text(text = "Ad Placeholder")
            }

            Row (
                Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.Center
            ){
                QuickStartButton(
                    uiState = uiState,
                    onClickStartButton = {
                        startButtonActionHandler(
                            viewModel = viewModel,
                            uiState = uiState,
                            context = context
                        )
                    }
                )
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            SingleChoiceSegmentedButtonRow{
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                        onClick = {
                            if(!uiState.segmentedButtonDisable){
                                viewModel.updateSegmentedButtonIndex(index)
                            }else{
                                Toast.makeText(context, "Segmented Button Disable", Toast.LENGTH_SHORT).show()
                            }
                        },
                        selected = index == uiState.segmentedButtonSelectedIndex
                    ) {
                        Text(label)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

fun startButtonActionHandler(
    viewModel: QuickActionViewModel,
    uiState: QuickActionUiState,
    context: Context
){
    if (uiState.segmentedButtonSelectedIndex == 0 && !uiState.startButtonStatus){
        // Selected screen off state, click to on
        /* TODO */
        Toast.makeText(context, "Screen flash is on", Toast.LENGTH_SHORT).show()
        viewModel.activeStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 0 && uiState.startButtonStatus){
        // Selected screen on state, click to off
        /* TODO */
        Toast.makeText(context, "Screen flash is off", Toast.LENGTH_SHORT).show()
        viewModel.stopStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 1 && !uiState.startButtonStatus){
        // Selected both off state, click to on
        /* TODO */
        Toast.makeText(context, "Both flash is on", Toast.LENGTH_SHORT).show()
        viewModel.activeStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 1 && uiState.startButtonStatus){
        // Selected both on state, click to off
        /* TODO */
        Toast.makeText(context, "Both flash is off", Toast.LENGTH_SHORT).show()
        viewModel.stopStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 2 && !uiState.startButtonStatus){
        // Selected flash off state, click to on
        /* TODO */
        Toast.makeText(context, "Back flash is on", Toast.LENGTH_SHORT).show()
        viewModel.activeStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 2 && uiState.startButtonStatus){
        // Selected flash off state, click to on
        /* TODO */
        Toast.makeText(context, "Back flash is off", Toast.LENGTH_SHORT).show()
        viewModel.stopStartButton()
    }
}


@Preview(showBackground = true)
@Composable
fun QuickActionScreenPreview(){
    val quickActionViewModel : QuickActionViewModel = viewModel()
    QuickActionScreen(quickActionViewModel)
}