package com.omniful.designsystem.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object ThemeManager {
    private val _customColors = mutableStateOf<OMFColors?>(null) // Default to LightColors
    val customColors: State<OMFColors?> = _customColors

    fun setCustomColors(colors: OMFColors) {
        _customColors.value = colors
    }

    private val _customTypography = mutableStateOf<OMFTypography?>(null)
    val customTypography: State<OMFTypography?> = _customTypography

    fun setCustomTypography(typography: OMFTypography) {
        _customTypography.value = typography

    }
}