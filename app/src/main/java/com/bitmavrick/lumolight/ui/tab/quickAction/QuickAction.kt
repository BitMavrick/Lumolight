/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.quickAction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
                .weight(1f).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Row(
                Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
            ) {
                val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1.5f), Modifier.weight(1f))

                options.forEachIndexed { index, label ->
                    ToggleButton(
                        checked = index == quickActionUiState.segmentedButtonSelectedIndex,
                        onCheckedChange = {
                            if (!quickActionUiState.segmentedButtonDisable) {
                                quickActionUiEvent(
                                    QuickActionUiEvent.UpdateSegmentedButtonIndex(
                                        index
                                    )
                                )
                            } else {
                                scope.launch {
                                    snakeBarHost.showSnackbar(
                                        message = context.getString(R.string.cant_change_flash),
                                        withDismissAction = true
                                    )
                                }
                            }
                        },
                        modifier = modifiers[index].semantics { role = Role.RadioButton },
                        shapes =
                            when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            }
                    ) {
                        Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
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
    val snackBarHostState = remember { SnackbarHostState() }
    QuickActionScreen(
        navController = rememberNavController(),
        quickActionUiState = QuickActionUiState(),
        quickActionUiEvent = {},
        snakeBarHost = snackBarHostState
    )
}