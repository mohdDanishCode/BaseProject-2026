package com.omniful.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


interface OMFShadows {
    val theme: ShadowLevels
    val brand: BrandShadows
}
data class ShadowStyle(
    val offsetX: Float,
    val offsetY: Float,
    val blur: Float,
    val spread: Float = 0f,
    val color: Color
)

interface ShadowLevels {
    val S01: ShadowStyle
    val S02: ShadowStyle
    val S03: ShadowStyle
    val S04: ShadowStyle
    val S05: ShadowStyle
}

object DarkShadows : ShadowLevels {
    override val S01 = ShadowStyle(0f, 4f, 12f, 2f, Color(0x02000000))
    override val S02 = ShadowStyle(0f, 4f, 12f, 2f, Color(0x04000000))
    override val S03 = ShadowStyle(0f, 4f, 16f, 4f, Color(0x06000000))
    override val S04 = ShadowStyle(0f, 4f, 16f, 4f, Color(0x08000000))
    override val S05 = ShadowStyle(0f, 4f, 16f, 4f, Color(0x10000000))
}

object LightShadows : ShadowLevels {
    override val S01 = ShadowStyle(0f, 4f, 12f, 2f, Color(0x02FFFFFF))
    override val S02 = ShadowStyle(0f, 4f, 12f, 2f, Color(0x04FFFFFF))
    override val S03 = ShadowStyle(0f, 4f, 16f, 4f, Color(0x06FFFFFF))
    override val S04 = ShadowStyle(0f, 4f, 16f, 4f, Color(0x08FFFFFF))
    override val S05 = ShadowStyle(0f, 4f, 16f, 4f, Color(0x10FFFFFF))
}


class BrandShadows(private val primary: Color) : ShadowLevels {

    override val S01 = ShadowStyle(0f, 4f, 12f, 2f, primary.copy(alpha = 0.02f))
    override val S02 = ShadowStyle(0f, 4f, 12f, 2f, primary.copy(alpha = 0.04f))
    override val S03 = ShadowStyle(0f, 4f, 16f, 4f, primary.copy(alpha = 0.06f))
    override val S04 = ShadowStyle(0f, 4f, 16f, 4f, primary.copy(alpha = 0.08f))
    override val S05 = ShadowStyle(0f, 4f, 16f, 4f, primary.copy(alpha = 0.10f))
}

fun Modifier.applyShadow(
    style: ShadowStyle,
    cornerRadius: Dp = 0.dp
) = this.shadow(
    elevation = style.blur.dp,
    spotColor = style.color,
    ambientColor = style.color,
    shape = RoundedCornerShape(cornerRadius)
)