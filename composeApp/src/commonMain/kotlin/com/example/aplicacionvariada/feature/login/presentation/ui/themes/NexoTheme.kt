package com.example.nexogestion.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val NexoColorScheme = lightColorScheme(
    primary = NexoColors.Primary,
    secondary = NexoColors.AccentPremium,
    background = NexoColors.Background,
    surface = NexoColors.Surface,
    onPrimary = NexoColors.Surface,
    onSecondary = NexoColors.TextPrimary,
    onBackground = NexoColors.TextPrimary,
    onSurface = NexoColors.TextPrimary
)

@Composable
fun NexoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NexoColorScheme,
        typography = NexoTypography,
        content = content
    )
}
