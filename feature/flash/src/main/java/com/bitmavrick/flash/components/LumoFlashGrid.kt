package com.bitmavrick.flash.components

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bitmavrick.data.domain.model.LumoFlash
import com.bitmavrick.flash.FlashlightUiEvent
import com.bitmavrick.lumoflash.activity.LumoFlashActivity
import com.bitmavrick.ui.components.DeleteFlashDialog
import com.bitmavrick.ui.details.FlashDetailsBottomSheet
import com.bitmavrick.ui.flashcard.LumoFlashCard
import kotlinx.coroutines.delay
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyStaggeredGridState

@Composable
fun LumoFlashGrid(
    flashList: List<LumoFlash>,
    modifier: Modifier = Modifier,
    onEvent: (FlashlightUiEvent) -> Unit
) {
    val context = LocalContext.current
    var list by remember(flashList) { mutableStateOf(flashList) }
    var previousSize by remember { mutableIntStateOf(flashList.size) }
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    LaunchedEffect(flashList) {
        if (flashList.size > previousSize) {
            lazyStaggeredGridState.animateScrollToItem(0)
        }
        previousSize = flashList.size
        list = flashList
        delay(100)
        onEvent(FlashlightUiEvent.ReorderBothFlash(list))
    }

    val reorderableLazyStaggeredGridState =
        rememberReorderableLazyStaggeredGridState(lazyStaggeredGridState) { from, to ->
            list = list.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
        state = lazyStaggeredGridState,
        modifier = modifier.fillMaxSize().padding(horizontal = 8.dp)
    ) {
        itemsIndexed(list, key = { _, item -> item.id }) { _, item ->
            var showUpdateDetailsBottomSheet by remember { mutableStateOf(false)  }
            var showDeleteDetailsBottomSheet by remember { mutableStateOf(false)  }

            ReorderableItem(reorderableLazyStaggeredGridState, item.id) {
                LumoFlashCard(
                    flash = item,
                    onPower = {
                        val intent = Intent(context, LumoFlashActivity::class.java).apply {
                            putExtra("flash_id", item.id)
                        }
                        context.startActivity(intent)
                    },
                    onEdit = {
                        showUpdateDetailsBottomSheet = true
                    },
                    onDelete = {
                        showDeleteDetailsBottomSheet = true
                    },
                    onAddToHome = {
                        if(item.isPinned){
                            onEvent(FlashlightUiEvent.RemoveFromHome(item))
                        }else{
                            onEvent(FlashlightUiEvent.AddToHome(item))
                        }
                    },
                    dragHandleModifier = Modifier.draggableHandle(
                        onDragStopped = {
                            onEvent(FlashlightUiEvent.ReorderBothFlash(list))
                        }
                    )
                )
            }

            if(showUpdateDetailsBottomSheet){
                FlashDetailsBottomSheet(
                    updateFlash = item,
                    flashType = item.flashType,
                    onUpdate = {
                        onEvent(FlashlightUiEvent.UpdateFlash(it))
                    },
                    onDismiss = {
                        showUpdateDetailsBottomSheet = false
                    }
                )
            }

            if(showDeleteDetailsBottomSheet){
                DeleteFlashDialog(
                    flash = item,
                    onDelete = {
                        onEvent(FlashlightUiEvent.DeleteFlash(it))
                        showDeleteDetailsBottomSheet = false
                    },
                    onCancel = {
                        showDeleteDetailsBottomSheet = false
                    }
                )
            }
        }
    }
}