package com.mako.taskmngr.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val BrandPrimary = Color(0xFFCAAB7D)

val ColorScheme.brand: Color
    get() = BrandPrimary

// Light theme colors
private val LightPrimary = Color(0xFFCAAB7D)
private val LightOnPrimary = Color(0xFF3D2F16)
private val LightPrimaryContainer = Color(0xFFE8D5B3)
private val LightOnPrimaryContainer = Color(0xFF2A1F0F)

private val LightSecondary = Color(0xFF6B5D4F)
private val LightOnSecondary = Color(0xFFFFFFFF)
private val LightSecondaryContainer = Color(0xFFF4E0CF)
private val LightOnSecondaryContainer = Color(0xFF241910)

private val LightTertiary = Color(0xFF51643F)
private val LightOnTertiary = Color(0xFFFFFFFF)
private val LightTertiaryContainer = Color(0xFFD3EABB)
private val LightOnTertiaryContainer = Color(0xFF102004)

private val LightError = Color(0xFFBA1A1A)
private val LightOnError = Color(0xFFFFFFFF)
private val LightErrorContainer = Color(0xFFFFDAD6)
private val LightOnErrorContainer = Color(0xFF410002)

private val LightBackground = Color(0xFFFFFBFF)
private val LightOnBackground = Color(0xFF1F1B16)
private val LightSurface = Color(0xFFFFFBFF)
private val LightOnSurface = Color(0xFF1F1B16)
private val LightSurfaceVariant = Color(0xFFF0E0CF)
private val LightOnSurfaceVariant = Color(0xFF4F4539)
private val LightOutline = Color(0xFF817567)
private val LightOutlineVariant = Color(0xFFD3C4B4)

// Dark theme colors
private val DarkPrimary = Color(0xFFE0C9A3)
private val DarkOnPrimary = Color(0xFF3E2E16)
private val DarkPrimaryContainer = Color(0xFF57442A)
private val DarkOnPrimaryContainer = Color(0xFFFDE6C4)

private val DarkSecondary = Color(0xFFD7C3B4)
private val DarkOnSecondary = Color(0xFF3A2F24)
private val DarkSecondaryContainer = Color(0xFF514539)
private val DarkOnSecondaryContainer = Color(0xFFF4DFCF)

private val DarkTertiary = Color(0xFFB7CEA1)
private val DarkOnTertiary = Color(0xFF243516)
private val DarkTertiaryContainer = Color(0xFF3A4C2A)
private val DarkOnTertiaryContainer = Color(0xFFD3EABB)

private val DarkError = Color(0xFFFFB4AB)
private val DarkOnError = Color(0xFF690005)
private val DarkErrorContainer = Color(0xFF93000A)
private val DarkOnErrorContainer = Color(0xFFFFDAD6)

private val DarkBackground = Color(0xFF17130F)
private val DarkOnBackground = Color(0xFFEBE1D9)
private val DarkSurface = Color(0xFF17130F)
private val DarkOnSurface = Color(0xFFEBE1D9)
private val DarkSurfaceVariant = Color(0xFF4F4539)
private val DarkOnSurfaceVariant = Color(0xFFD3C4B4)
private val DarkOutline = Color(0xFF9C8F80)
private val DarkOutlineVariant = Color(0xFF4F4539)

val TmLightColors = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant
)

val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant
)
