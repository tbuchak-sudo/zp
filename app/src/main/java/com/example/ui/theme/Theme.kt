package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HighDensityColorScheme = lightColorScheme(
    primary = HighDensityPrimary,
    onPrimary = HighDensityOnPrimary,
    primaryContainer = HighDensityPrimaryContainer,
    onPrimaryContainer = HighDensityOnPrimaryContainer,
    secondary = HighDensitySecondary,
    onSecondary = Color.White,
    secondaryContainer = HighDensitySecondaryContainer,
    onSecondaryContainer = HighDensityOnSecondaryContainer,
    tertiary = HighDensityPrimaryDark,
    onTertiary = Color.White,
    tertiaryContainer = HighDensityPrimaryLight,
    onTertiaryContainer = HighDensityPrimaryDark,
    background = HighDensityBg,
    onBackground = HighDensityTextPrimary,
    surface = HighDensitySurface,
    onSurface = HighDensityTextPrimary,
    surfaceVariant = HighDensitySurfaceVariant,
    onSurfaceVariant = HighDensityTextSecondary,
    outline = HighDensityBorder,
    outlineVariant = HighDensityBorderSubtle,
    error = BaderRed,
    onError = Color.White,
    errorContainer = HighDensityRoseContainer,
    onErrorContainer = HighDensityRoseText
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HighDensityColorScheme,
        typography = Typography,
        content = content
    )
}

