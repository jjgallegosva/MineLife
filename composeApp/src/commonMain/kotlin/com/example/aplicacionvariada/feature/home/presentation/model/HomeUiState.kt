package com.example.aplicacionvariada.feature.home.presentation.model

import com.example.aplicacionvariada.feature.home.domain.MineRegimeStatus

data class HomeUiState(
    val isLoading: Boolean = true,
    val regimeStatus: MineRegimeStatus? = null,
    val errorMessage: String? = null
)