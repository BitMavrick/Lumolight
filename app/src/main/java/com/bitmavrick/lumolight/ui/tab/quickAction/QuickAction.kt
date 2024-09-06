/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.util.AppConstants
import com.bitmavrick.lumolight.util.ProductionMode
import kotlinx.coroutines.launch

@Composable
fun QuickActionScreen(
    navController: NavController,
    quickActionUiState: QuickActionUiState,
    quickActionUiEvent: (QuickActionUiEvent) -> Unit,
    snakeBarHost: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val options = listOf(
        context.getString(R.string.screen),
        context.getString(R.string.both),
        context.getString(R.string.flash)
    )

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
            Row (
                Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.Center
            ){
                QuickStartButton(
                    uiState = quickActionUiState,
                    onClick = {
                        startButtonActionHandler(
                            navController = navController,
                            uiState = quickActionUiState,
                            uiEvent = quickActionUiEvent,
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
                            if(!quickActionUiState.segmentedButtonDisable){
                                quickActionUiEvent(QuickActionUiEvent.UpdateSegmentedButtonIndex(index))
                            }else{
                                scope.launch {
                                    snakeBarHost.showSnackbar(
                                        message = context.getString(R.string.cant_change_flash),
                                        withDismissAction = true
                                    )
                                }
                            }
                        },
                        selected = index == quickActionUiState.segmentedButtonSelectedIndex
                    ) {
                        Text(label)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if(Build.VERSION.SDK_INT >= 33){
                Text(
                    text = getTorchStrengthValue(LocalContext.current).toString()
                )
            }else{
                Text("Unsupported Value")
            }
        }
    }
}

// ** Experimental Systems **
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun getTorchStrengthValue(context: Context) : Int {
    return if(AppConstants.APP_PRODUCTION_MODE == ProductionMode.RELEASE){
        val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)

        val torchMaxLevel = cameraCharacteristics[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL] ?: -1

        torchMaxLevel
    }else{
        -10
    }
}

@Preview(showBackground = true)
@Composable
fun QuickActionScreenPreview(){
    val snackBarHostState = remember { SnackbarHostState() }
    QuickActionScreen(
        navController = rememberNavController(),
        quickActionUiState = QuickActionUiState(),
        quickActionUiEvent = {},
        snakeBarHost = snackBarHostState
    )
}