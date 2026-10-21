package com.example.aplicacionvariada.feature.home.domain

import com.example.aplicacionvariada.feature.calendar.domain.RegimeSettings
import kotlinx.datetime.LocalDate

class CalculateMineRegimeUseCase {

    operator fun invoke(
        settings: RegimeSettings,
        today: LocalDate
    ): MineRegimeStatus {
        val mineDays = settings.mineDays.coerceAtLeast(1)
        val restDays = settings.restDays.coerceAtLeast(0)
        val cycleLength = mineDays + restDays

        val parts = settings.firstMineDate.split("/")

        val startDate = runCatching {
            require(parts.size == 3)

            LocalDate(
                parts[2].toInt(),
                parts[1].toInt(),
                parts[0].toInt()
            )
        }.getOrNull()

        val validStartDate = startDate ?: today

        if (startDate == null || today < startDate) {
            val daysUntilStart = if (startDate != null) {
                startDate.toEpochDays() - today.toEpochDays()
            } else {
                0
            }

            return MineRegimeStatus(
                isBeforeStart = true,
                isInMine = false,
                currentDay = 0,
                cycleLength = cycleLength,
                mineDays = mineDays,
                restDays = restDays,
                daysRemaining = daysUntilStart.toInt(),
                relevantDate = validStartDate,
                progress = 0f
            )
        }

        val elapsed = today.toEpochDays() - startDate.toEpochDays()
        val cycleDay = (elapsed % cycleLength).toInt()
        val isInMine = cycleDay < mineDays

        val cycleStart = LocalDate.fromEpochDays(
            startDate.toEpochDays() +
                    (elapsed / cycleLength) * cycleLength
        )

        val relevantDate = if (isInMine) {
            LocalDate.fromEpochDays(
                cycleStart.toEpochDays() + mineDays - 1
            )
        } else {
            LocalDate.fromEpochDays(
                cycleStart.toEpochDays() + cycleLength
            )
        }

        return MineRegimeStatus(
            isBeforeStart = false,
            isInMine = isInMine,
            currentDay = cycleDay + 1,
            cycleLength = cycleLength,
            mineDays = mineDays,
            restDays = restDays,
            daysRemaining = (
                    relevantDate.toEpochDays() - today.toEpochDays()
                    ).toInt().coerceAtLeast(0),
            relevantDate = relevantDate,
            progress = (cycleDay + 1).toFloat() / cycleLength
        )
    }
}
