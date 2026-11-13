package com.example.dessertclicker

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DessertUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessert: Dessert = Datasource.dessertList.first()
)

class DessertViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    fun onDessertClicked() {
        val state = _uiState.value
        val newRevenue = state.revenue + state.currentDessert.price
        val newDessertsSold = state.dessertsSold + 1
        val newDessert = determineDessertToShow(
            desserts = Datasource.dessertList,
            dessertsSold = newDessertsSold
        )
        _uiState.value = state.copy(
            revenue = newRevenue,
            dessertsSold = newDessertsSold,
            currentDessert = newDessert
        )
    }
}

fun determineDessertToShow(
    desserts: List<Dessert>,
    dessertsSold: Int
): Dessert {
    var dessertToShow = desserts.first()
    for (dessert in desserts) {
        if (dessertsSold >= dessert.startProductionAmount) {
            dessertToShow = dessert
        } else {
            break
        }
    }
    return dessertToShow
}