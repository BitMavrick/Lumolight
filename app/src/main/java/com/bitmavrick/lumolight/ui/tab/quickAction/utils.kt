package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context
import android.widget.Toast

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