package com.omniful.designsystem.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object ThemeManager {
    private val _customColors = mutableStateOf(null) // Default to LightColors
    val customColors: State<OMFColors?> = _customColors

    private val _customTypography = mutableStateOf(null)
    val customTypography: State<OMFTypography?> = _customTypography
}