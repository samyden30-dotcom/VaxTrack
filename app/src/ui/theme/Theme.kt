package com.example.vaxtrack.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = BleuMedical,
    secondary = CyanPur,
    background = BlancChirurgical,
    surface = BlancChirurgical,
    error = DangerRed,
    onPrimary = BlancChirurgical,
    onSecondary = BlancChirurgical,
    onBackground = BleuMedical,
    onSurface = BleuMedical,
    onError = BlancChirurgical
)

@Composable
fun VaxTrackTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
