package com.bitmavrick.tiles

import androidx.lifecycle.ViewModel
import com.bitmavrick.data.domain.repository.ScreenColorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TilesViewModel @Inject constructor(
    private val repository: ScreenColorRepository
    // private val screenColorDao: ScreenColorDao
): ViewModel() {
    // * Code from here
}