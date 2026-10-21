package pe.compartamos.designsystem.components.molecules.calendar


object CalendarUtils {
    private val monthsSpanish = listOf(
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    fun getMonthName(month: Int): String {
        return monthsSpanish.getOrNull(month - 1) ?: "Mes"
    }
}
