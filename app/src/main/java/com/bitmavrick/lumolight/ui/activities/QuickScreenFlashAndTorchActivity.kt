package com.bitmavrick.lumolight.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.ui.screen.quickActions.QuickActionsViewModel
import com.bitmavrick.lumolight.ui.utils.KeepScreenOn
import com.bitmavrick.lumolight.ui.utils.UpdateBrightness


@Suppress("DEPRECATION")
class QuickScreenFlashAndTorchActivity : ComponentActivity() {

    private lateinit var viewModel: QuickActionsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            viewModel = viewModel()
            viewModel.toggleFlashLight(this, true)
            UpdateBrightness()
            KeepScreenOn()
            QuickScreenFlash(
                onExit = {
                    viewModel.toggleFlashLight(this, false)
                    onBackPressed()
                }
            )
        }
    }
}