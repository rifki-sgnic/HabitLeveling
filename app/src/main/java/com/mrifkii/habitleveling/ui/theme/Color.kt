package com.mrifkii.habitleveling.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Reference palettes (md.ref.palette.*)
val Primary80 = Color(0xFF4ABDFF)
val PrimaryContainer20 = Color(0xFF003547)
val Secondary80 = Color(0xFFA5CCE0)
val Tertiary80 = Color(0xFFCDB8FF)
val Error80 = Color(0xFFFFB3B8)
val Neutral10 = Color(0xFF0B0F14)
val Neutral6 = Color(0xFF05070A)
val Neutral12 = Color(0xFF0F1620)
val NeutralVariant50 = Color(0xFF6B7A8C)

val ShadowDarkColorScheme = darkColorScheme(
    primary = Color(0xFF4ABDFF),
    onPrimary = Color(0xFF001E2E),
    primaryContainer = Color(0xFF003547),
    onPrimaryContainer = Color(0xFFC9E9FF),
    secondary = Color(0xFFA5CCE0),
    onSecondary = Color(0xFF0E2A38),
    secondaryContainer = Color(0xFF274D63),
    onSecondaryContainer = Color(0xFFC0E2F0),
    tertiary = Color(0xFFCDB8FF),
    onTertiary = Color(0xFF341B6B),
    tertiaryContainer = Color(0xFF492F7C),
    onTertiaryContainer = Color(0xFFE7DBFF),
    error = Color(0xFFFFB3B8),
    onError = Color(0xFF67001D),
    errorContainer = Color(0xFF910029),
    onErrorContainer = Color(0xFFFFDADD),
    background = Color(0xFF05070A),
    onBackground = Color(0xFFE6F1FF),
    surface = Color(0xFF05070A),
    onSurface = Color(0xFFE6F1FF),
    surfaceVariant = Color(0xFF1A2230),
    onSurfaceVariant = Color(0xFFB8C5D8),
    outline = Color(0xFF6B7A8C),
    outlineVariant = Color(0xFF3A4757),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0B0F14),
    surfaceContainer = Color(0xFF0F1620),
    surfaceContainerHigh = Color(0xFF131C28),
    surfaceContainerHighest = Color(0xFF1A2230),
    inverseSurface = Color(0xFFE6F1FF),
    inverseOnSurface = Color(0xFF0F1620),
    inversePrimary = Color(0xFF1A6FA8),
    scrim = Color(0xFF000000),
)

val ShadowLightColorScheme = lightColorScheme(
    primary = Color(0xFF1A6FA8),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFC9E9FF),
    onPrimaryContainer = Color(0xFF001E2E),
    background = Color(0xFFF8FCFF),
    surface = Color(0xFFF8FCFF),
    onBackground = Color(0xFF0B0F14),
    onSurface = Color(0xFF0B0F14),
    surfaceContainer = Color(0xFFE9F2FB),
    outline = Color(0xFF6B7A8C),
    // Standard M3 light tones for others
)

@Immutable
data class ShadowCustomColors(
    val success: ColorRole,
    val warning: ColorRole,
    val xp: ColorRole,
    val hp: ColorRole,
    val mp: ColorRole,
    val rankE: ColorRole,
    val rankD: ColorRole,
    val rankC: ColorRole,
    val rankB: ColorRole,
    val rankA: ColorRole,
    val rankS: ColorRole,
    val rankNational: ColorRole
)

@Immutable
data class ColorRole(
    val color: Color,
    val onColor: Color,
    val container: Color,
    val onContainer: Color
)

val ShadowDarkExtended = ShadowCustomColors(
    success = ColorRole(Color(0xFF4AFFB5), Color(0xFF003824), Color(0xFF005237), Color(0xFF71FFD0)),
    warning = ColorRole(Color(0xFFFFB547), Color(0xFF3F2700), Color(0xFF5B3B00), Color(0xFFFFDDAE)),
    xp = ColorRole(Color(0xFF4ABDFF), Color(0xFF001E2E), Color(0xFF003547), Color(0xFFC9E9FF)),
    hp = ColorRole(Color(0xFFFFB3B8), Color(0xFF67001D), Color(0xFF910029), Color(0xFFFFDADD)),
    mp = ColorRole(Color(0xFFCDB8FF), Color(0xFF341B6B), Color(0xFF492F7C), Color(0xFFE7DBFF)),
    rankE = ColorRole(Color(0xFF6B7A8C), Color(0xFF0B0F14), Color(0xFF1A2230), Color(0xFFB8C5D8)),
    rankD = ColorRole(Color(0xFFA5CCE0), Color(0xFF0E2A38), Color(0xFF274D63), Color(0xFFC0E2F0)),
    rankC = ColorRole(Color(0xFF4ABDFF), Color(0xFF001E2E), Color(0xFF003547), Color(0xFFC9E9FF)),
    rankB = ColorRole(Color(0xFF7FD4FF), Color(0xFF001E2E), Color(0xFF003547), Color(0xFFC9E9FF)),
    rankA = ColorRole(Color(0xFFCDB8FF), Color(0xFF341B6B), Color(0xFF492F7C), Color(0xFFE7DBFF)),
    rankS = ColorRole(Color(0xFFFFD27A), Color(0xFF3F2700), Color(0xFF5B3B00), Color(0xFFFFDDAE)),
    rankNational = ColorRole(Color(0xFFFF8A8E), Color(0xFF410012), Color(0xFF67001D), Color(0xFFFFDADD))
)

val LocalShadowColors = staticCompositionLocalOf { ShadowDarkExtended }
