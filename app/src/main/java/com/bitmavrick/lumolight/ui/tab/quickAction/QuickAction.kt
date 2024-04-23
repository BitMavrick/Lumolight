package com.bitmavrick.lumolight.ui.tab.quickAction

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickActionScreen(
    viewModel: QuickActionViewModel
) {
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
                    uiState = QuickActionsUiState(),
                    onClickStartButton = {}
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
                                /* TODO */
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


@Preview(showBackground = true)
@Composable
fun QuickActionScreenPreview(){
    val quickActionViewModel : QuickActionViewModel = viewModel()
    QuickActionScreen(quickActionViewModel)
}