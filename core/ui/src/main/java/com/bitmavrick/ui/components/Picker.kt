package com.bitmavrick.ui.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun Picker(
    items: List<String>,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    visibleItemsCount: Int = 3,
    onItemSelected: (Int) -> Unit = {}
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightDp = 48.dp

    val selectedItemIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }

    LaunchedEffect(selectedItemIndex) {
        // Guard: Only report change if it was caused by manual scrolling (index != initialIndex)
        if (selectedItemIndex < items.size && selectedItemIndex != initialIndex) {
            delay(100)
            onItemSelected(selectedItemIndex)
        }
    }

    LaunchedEffect(initialIndex) {
        // Sync internal state when external source of truth changes
        if (initialIndex != listState.firstVisibleItemIndex) {
            listState.scrollToItem(initialIndex)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp)
                .align(Alignment.Center)
        ) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }

        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
        ) {
            items(visibleItemsMiddle) {
                Box(modifier = Modifier.height(itemHeightDp))
            }

            items(items.size) { index ->
                val isSelected = index == selectedItemIndex
                val alpha by remember(index, selectedItemIndex) {
                    derivedStateOf {
                        val distance = Math.abs(index - selectedItemIndex)
                        if (distance == 0) 1f else if (distance == 1) 0.5f else 0.2f
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeightDp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[index],
                        modifier = Modifier.alpha(alpha),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = if (isSelected) 24.sp else 20.sp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else LocalContentColor.current
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(visibleItemsMiddle) {
                Box(modifier = Modifier.height(itemHeightDp))
            }
        }
    }
}
