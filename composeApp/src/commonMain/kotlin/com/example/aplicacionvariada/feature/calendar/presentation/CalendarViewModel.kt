package com.example.aplicacionvariada.feature.calendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.calendar.domain.CalendarCalculator
import com.example.aplicacionvariada.feature.calendar.domain.RegimeSettings
import com.example.aplicacionvariada.feature.calendar.presentation.model.CalendarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

/** Lee/guarda el régimen y mantiene los resultados calculados para la pantalla. */
class CalendarViewModel(
    private val regimeDataStore: RegimeDataStore
) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState = _uiState.asStateFlow()

    private var activeMonthIndex: Int = 9
    private var activeYear: Int = 2026
    private var today: LocalDate = LocalDate(2026, 10, 21)

    init {
        viewModelScope.launch {
            regimeDataStore.settings
                .catch { error ->
                    _uiState.update {
                        it.copy(errorMessage = error.message ?: "No se pudo cargar el régimen")
                    }
                }
                .collect { settings ->
                    _uiState.update {
                        it.copy(
                            mineDays = settings.mineDays,
                            restDays = settings.restDays,
                            firstMineDate = settings.firstMineDate,
                            regimeName = settings.regimeName,
                            regimeNotes = settings.regimeNotes,
                            errorMessage = null
                        )
                    }
                    recalculateCalendar()
                }
        }
    }

    /** La pantalla informa solo qué mes se está mostrando; el cálculo vive aquí/dominio. */
    fun updateCalendar(monthIndex: Int, year: Int, today: LocalDate) {
        activeMonthIndex = monthIndex.coerceIn(0, 11)
        activeYear = year.coerceIn(1, 9999)
        this.today = today
        recalculateCalendar()
    }

    private fun recalculateCalendar() {
        val current = _uiState.value
        val result = CalendarCalculator.calculate(
            monthIndex = activeMonthIndex,
            year = activeYear,
            mineDays = current.mineDays,
            restDays = current.restDays,
            firstMineDate = current.firstMineDate,
            today = today
        )
        _uiState.update {
            it.copy(
                days = result.days,
                monthlySummary = result.monthlySummary,
                yearlySummary = result.yearlySummary,
                cycleLength = result.cycleLength,
                todayCycleDay = result.todayCycleDay,
                nextMineDate = result.nextMineDate,
                nextRestDate = result.nextRestDate,
                previousCycles = result.previousCycles
            )
        }
    }

    fun saveRegime(
        mineDays: Int,
        restDays: Int,
        firstMineDate: String,
        regimeName: String,
        regimeNotes: String
    ) {
        if (mineDays <= 0 || restDays <= 0) {
            _uiState.update {
                it.copy(errorMessage = "Los días de mina y descanso deben ser mayores que cero")
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }
            try {
                regimeDataStore.save(
                    RegimeSettings(
                        mineDays = mineDays,
                        restDays = restDays,
                        firstMineDate = firstMineDate,
                        regimeName = regimeName,
                        regimeNotes = regimeNotes
                    )
                )
            } catch (error: Throwable) {
                _uiState.update {
                    it.copy(errorMessage = error.message ?: "No se pudo guardar el régimen")
                }
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}
