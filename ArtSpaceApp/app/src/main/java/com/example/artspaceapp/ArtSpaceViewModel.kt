package com.example.artspaceapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// UI-состояние экрана
data class ArtSpaceUiState(
    val currentArtworkIndex: Int = 0,
    val totalArtworks: Int = 3
)

class ArtSpaceViewModel : ViewModel() {

    // Приватное изменяемое состояние — SSOT
    private val _uiState = MutableStateFlow(ArtSpaceUiState())
    // Публичное неизменяемое состояние для UI
    val uiState: StateFlow<ArtSpaceUiState> = _uiState.asStateFlow()

    fun nextArtwork() {
        val state = _uiState.value
        if (state.currentArtworkIndex < state.totalArtworks - 1) {
            _uiState.value = state.copy(currentArtworkIndex = state.currentArtworkIndex + 1)
        }
    }

    fun previousArtwork() {
        val state = _uiState.value
        if (state.currentArtworkIndex > 0) {
            _uiState.value = state.copy(currentArtworkIndex = state.currentArtworkIndex - 1)
        }
    }
}