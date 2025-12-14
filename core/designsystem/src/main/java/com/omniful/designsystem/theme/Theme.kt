package com.omniful.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

interface OMFColors {
    val primary: Color
    val secondary: Color
    val background: Color
    val surface: Color
}

data class LightColors(
    override val primary: Color = Orange100,
    override val secondary: Color = Blue100,
    override val background: Color = White100,
    override val surface: Color = White100
):OMFColors{

}

data class DarkColors(
    override val primary: Color = Orange100,
    override val secondary: Color = Blue100,
    override val background: Color = Color.Black,
    override val surface: Color = Color.Black
):OMFColors

data class CustomColors(
    override val primary: Color = Orange100,
    override val secondary: Color = Blue100,
    override val background: Color = Color.Blue,
    override val surface: Color = Color.Blue
):OMFColors




val LocalOMFColors = staticCompositionLocalOf<OMFColors> {
    error("No Color provided")
}



val LocalTypography = staticCompositionLocalOf<OMFTypography> {
    error("No Typography provided")
}

val LocalShadows = staticCompositionLocalOf<OMFShadows> {
    error("No shadow provided")
}

val LocalOMFSize = staticCompositionLocalOf<OMFSize> {
    error("No OMFSize provided")
}


@Composable
fun OmnifulTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) DarkColors() else LightColors()
    val typography = if (darkTheme) DarkTypography() else LightTypography()

    val configuredColorState = ThemeManager.customColors.value ?: colors

    val configuredTypographyState = ThemeManager.customTypography.value ?: typography

    // THEME shadows → light or dark
    val themeShadows = if (darkTheme) DarkShadows else LightShadows

    // BRAND shadows → generated from primary
    val brandShadows = BrandShadows(primary = configuredColorState.primary)

    val configuredSize = DefaultOMFSize


    val shadows = object : OMFShadows {
        override val theme = themeShadows
        override val brand = brandShadows
    }

    CompositionLocalProvider(
        LocalOMFSize provides configuredSize,
        LocalOMFColors provides configuredColorState,
        LocalTypography provides configuredTypographyState,
        LocalShadows provides shadows
    ) {
        content()
    }
}