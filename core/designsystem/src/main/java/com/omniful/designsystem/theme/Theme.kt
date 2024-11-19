package com.omniful.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class OMFColors(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color
)



val LocalOMFColors = staticCompositionLocalOf {
    OMFColors(
        primary = Color.Unspecified,
        secondary = Color.Unspecified,
        background = Color.Unspecified,
        surface = Color.Unspecified
    )
}

val LocalOMFTypography = staticCompositionLocalOf {
    OMFTypography(
        heading = TextStyle.Default,
        body = TextStyle.Default,
        caption = TextStyle.Default
    )
}

val LightColors = OMFColors(
    primary = Purple30,
    secondary = Purple80,
    background = White100,
    surface = White100
)

val DarkColors = OMFColors(
    primary = Purple30,
    secondary = Purple80,
    background = Color.Black,
    surface = Color.Black
)

@Composable
fun OmnifulTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) DarkColors else LightColors
    val configuredColorState = ThemeManager.customColors.value?.copy(
        primary = colors.primary,
        secondary = colors.secondary,
        background = colors.background,
        surface = colors.surface
    ) ?: colors
    val typography = if (darkTheme) DarkTypography else LightTypography

    CompositionLocalProvider(
        LocalOMFColors provides configuredColorState,
        LocalOMFTypography provides typography
    ) {
        content()
    }
}