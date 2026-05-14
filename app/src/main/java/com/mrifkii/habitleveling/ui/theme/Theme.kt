package com.mrifkii.habitleveling.ui.theme

import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun HabitLevelingTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = ShadowDarkColorScheme
    val extendedColors = ShadowDarkExtended

    CompositionLocalProvider(
        LocalShadowColors provides extendedColors,
        LocalShadowTypography provides ShadowExtendedTypescale,
        LocalRippleConfiguration provides RippleConfiguration(color = Color.Transparent)
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = ShadowTypography,
            shapes = ShadowShapes,
            content = content
        )
    }
}
