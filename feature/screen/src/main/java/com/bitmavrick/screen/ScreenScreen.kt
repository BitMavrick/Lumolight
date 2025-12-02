package com.bitmavrick.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness5
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.screen.activity.ScreenFlashActivity
import com.bitmavrick.screen.components.card.BrightnessCard
import com.bitmavrick.screen.components.card.ColorCard
import com.bitmavrick.screen.components.card.DurationCard
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenScreen(
    uiState: ScreenFlashUiState,
    onEvent: (ScreenFlashUiEvent) -> Unit
){
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                imageVector = Icons.Rounded.Brightness5,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.padding(4.dp))

                            Text(
                                text = stringResource(localesR.string.screen_topbar),
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )

                HorizontalDivider(Modifier.fillMaxWidth())
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
                .padding(innerPadding),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item { Spacer(Modifier.height(4.dp)) }

                item {
                    ColorCard(
                        uiState = uiState,
                        onEvent = onEvent
                    )
                }

                item{
                    DurationCard(
                        uiState = uiState,
                        onEvent = onEvent
                    )
                }

                item {
                    BrightnessCard(
                        uiState = uiState,
                        onEvent = onEvent
                    )
                }
            }

            Card(
                modifier = Modifier.height(62.dp).padding(8.dp)
                    .clickable{
                        val intent = Intent(context, ScreenFlashActivity::class.java)
                        context.startActivity(intent)
                    },
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().background(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(localesR.string.start),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ScreenScreenPreview(){
    ScreenScreen(
        uiState = ScreenFlashUiState(),
        onEvent = {}
    )
}