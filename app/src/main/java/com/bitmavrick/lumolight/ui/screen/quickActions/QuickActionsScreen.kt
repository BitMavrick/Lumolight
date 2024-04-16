package com.bitmavrick.lumolight.ui.screen.quickActions

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.ui.activities.QuickScreenFlashActivity
import com.bitmavrick.lumolight.ui.activities.QuickScreenFlashAndTorchActivity
import com.bitmavrick.lumolight.ui.components.quickActions.QuickSOSButton
import com.bitmavrick.lumolight.ui.components.quickActions.QuickStartButton
import com.bitmavrick.lumolight.ui.utils.GoogleAds
import com.bitmavrick.lumolight.ui.utils.LumolightNavigationType
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickActionsScreen(
    navigationType: LumolightNavigationType
) {
    val viewModel: QuickActionsViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val options = listOf("Front", "Both", "Back")
    val context = LocalContext.current

    if(uiState.segmentedButtonIndex == 0 || uiState.segmentedButtonIndex == 1){
        viewModel.stopStartButton()
    }

    if(navigationType == LumolightNavigationType.BOTTOM_NAVIGATION || navigationType == LumolightNavigationType.NAVIGATION_RAIL){
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ){
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Quick Actions",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(top = 24.dp)
                    )

                    // BannerAd()
                    Text(text = "Banner Ad Placeholder")

                    Spacer(modifier = Modifier.height(1.dp))
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
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

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SingleChoiceSegmentedButtonRow{
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                                onClick = { viewModel.updateSegmentedButtonStatus(index) },
                                selected = index == uiState.segmentedButtonIndex,
                            ) {
                                Text(label)
                            }
                        }
                    }
                    QuickSOSButton(viewModel)
                }
            }
        }
    } else if(navigationType == LumolightNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Row(
                Modifier
                    .weight(1f, true)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }

            Row(
                Modifier
                    .weight(3.5f, true)
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    QuickSOSButton(viewModel)

                    SingleChoiceSegmentedButtonRow {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                                onClick = { viewModel.updateSegmentedButtonStatus(index) },
                                selected = index == uiState.segmentedButtonIndex
                            ) {
                                Text(label)
                            }
                        }
                    }
                }

                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        Modifier.padding(8.dp)
                    ) {
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
            }

            Row(
                Modifier
                    .weight(1f, true)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // BannerAd()
                Text(text = "Banner Ad Placeholder")
            }
        }
    }
}

@Composable
fun BannerAd(){
    AndroidView(factory = {context ->
        AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = GoogleAds.ID
            loadAd(AdRequest.Builder().build())
        }
    })
}

fun startButtonActionHandler(
    viewModel: QuickActionsViewModel,
    uiState: QuickActionsUiState,
    context: Context
){
    if (uiState.segmentedButtonIndex == 0){
        viewModel.loadingStartButtonWithTimer(1)
        Intent(context, QuickScreenFlashActivity::class.java).also {
            context.startActivity(it)
        }
    }


    if(uiState.segmentedButtonIndex == 1){
        viewModel.loadingStartButtonWithTimer(1)
        Intent(context, QuickScreenFlashAndTorchActivity::class.java).also {
            context.startActivity(it)
        }
    }

    if(uiState.segmentedButtonIndex == 2){
        if(uiState.startButtonStatus){
            // When its on
            viewModel.loadingStartButton(true)
            viewModel.toggleFlashLight(context, false)
            viewModel.stopStartButton()
            viewModel.loadingStartButton(false)
        }else{
            // When its off
            viewModel.loadingStartButton(true)
            viewModel.toggleFlashLight(context, true)
            viewModel.activeStartButton()
            viewModel.loadingStartButton(false)
        }
    }
}


@Preview(
    showBackground = true,
    // device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun QuickActionScreenPreview(){
    QuickActionsScreen(navigationType = LumolightNavigationType.BOTTOM_NAVIGATION)
}