package com.example.aplicacionvariada

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes:NavKey {

    @Serializable
    data object Splash : Routes()
    @Serializable
    data object Login : Routes()
    @Serializable
    data object Register : NavKey
    @Serializable
    data class ForggetPossward(val id: String) : Routes()

    @Serializable
    data object Home : Routes()
    @Serializable
    data object Calendar : Routes()
    @Serializable
    data object Error : Routes()

    @Serializable
    data object ConsultarJornada : Routes()

    object RecoverPassword : Routes()

    @Serializable
    data class JornadaCalendar(
        val year: Int,
        val month: Int,
        val day: Int,
        val patternName: String,
        val range: Int
    ) : Routes()

}
