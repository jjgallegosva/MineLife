package com.example.aplicacionvariada.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.home.domain.CalculateMineRegimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class HomeViewModel(
    private val regimeDataStore: RegimeDataStore,
    private val calculateMineRegime: CalculateMineRegimeUseCase =
        CalculateMineRegimeUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState = _uiState.asStateFlow()

    init {
        observeRegimeSettings()
    }

    private fun observeRegimeSettings() {
        viewModelScope.launch {
            try {
                regimeDataStore.settings.collect { settings ->
                    val today = Clock.System.todayIn(
                        TimeZone.currentSystemDefault()
                    )

                    val status = calculateMineRegime(
                        settings = settings,
                        today = today
                    )

                    _uiState.value = HomeUiState(
                        isLoading = false,
                        regimeStatus = status
                    )
                }
            } catch (exception: Exception) {
                _uiState.value = HomeUiState(
                    isLoading = false,
                    errorMessage = exception.message
                        ?: "No se pudo cargar el régimen minero"
                )
            }
        }
    }
}
