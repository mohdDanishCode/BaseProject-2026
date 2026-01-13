package com.omniful.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface OMFCornerRadius {
    val radius0: Dp
    val radius1: Dp
    val radius2: Dp
    val radius3: Dp
    val radius4: Dp

    val radius5: Dp
    val radius6: Dp
    val radius7: Dp
    val radius8: Dp

    val radius9: Dp
    val radiusFull: Dp
}

interface OMFSpacing {
    val spacing0: Dp
    val spacing1: Dp
    val spacing2: Dp
    val spacing3: Dp
    val spacing4: Dp
    val spacing5: Dp
    val spacing6: Dp
    val spacing7: Dp
    val spacing8: Dp
}

object DefaultCornerRadius : OMFCornerRadius {

    override val radius0 = 0.dp
    override val radius1 = 4.dp
    override val radius2 = 8.dp
    override val radius3 = 12.dp
    override val radius4 = 16.dp
    override val radius5 = 20.dp
    override val radius6 = 24.dp
    override val radius7 = 28.dp
    override val radius8 = 32.dp
    override val radius9 = 36.dp

    override val radiusFull = 100.dp   // for pill / max rounding
}

object DefaultSpacing : OMFSpacing {

    override val spacing0 = 0.dp
    override val spacing1 = 4.dp
    override val spacing2 = 8.dp
    override val spacing3 = 12.dp
    override val spacing4 = 16.dp
    override val spacing5 = 20.dp
    override val spacing6 = 24.dp
    override val spacing7 = 28.dp
    override val spacing8 = 32.dp
}

interface OMFSize {
    val spacing: OMFSpacing
    val radius: OMFCornerRadius
}

object DefaultOMFSize : OMFSize {
    override val spacing = DefaultSpacing
    override val radius = DefaultCornerRadius
}