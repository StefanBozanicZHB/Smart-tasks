package com.zhbcompany.smarttasks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = Green,
    onPrimary = White,
    onSecondary = Green,
    onTertiary = Orange,
    background = LightYellow,
    surface = LightYellow,
    primaryContainer = White,
    onPrimaryContainer = Red,
    onSecondaryContainer = Red,
    onTertiaryContainer = DarkGrey,
    surfaceVariant = LightYellow,
    errorContainer = Red,
    onErrorContainer = White,
)

private val DarkColorScheme = darkColorScheme(
    primary = Green,
    onPrimary = White,
    onSecondary = Green,
    onTertiary = Orange,
    background = LightYellow,
    surface = LightYellow,
    primaryContainer = White,
    onPrimaryContainer = Red,
    onSecondaryContainer = Red,
    onTertiaryContainer = DarkGrey,
    surfaceVariant = LightYellow,
    errorContainer = Red,
    onErrorContainer = White,
)

@Composable
fun SmartTasksTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (!useDarkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

val AppShapes = Shapes(
    small = RoundedCornerShape(5.dp),
    medium = RoundedCornerShape(7.dp),
    large = RoundedCornerShape(10.dp)
)