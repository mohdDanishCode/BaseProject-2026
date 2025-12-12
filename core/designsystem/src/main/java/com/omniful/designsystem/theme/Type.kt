package com.omniful.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.omniful.designsystem.R

// -------------------------------------------------------------
// 1. Core Typography Interface
// -------------------------------------------------------------
interface OMFTypography {
    val heading: HeadingTypography
    val body: BodyTypography
    val caption: CaptionTypography
    val underline: UnderlineTypography
}

// -------------------------------------------------------------
// 2. Type Definitions (Heading / Body / Caption)
// -------------------------------------------------------------
sealed class HeadingType(val size: Int) {
    data object H01 : HeadingType(64)
    data object H02 : HeadingType(56)
    data object H03 : HeadingType(48)
    data object H04 : HeadingType(40)

    companion object {
        val entries = listOf(H01, H02, H03, H04)
    }
}

sealed class BodyType(val size: Int) {
    data object B01 : BodyType(16)
    data object B02 : BodyType(14)
    data object B03 : BodyType(12)

    companion object {
        val entries = listOf(B01, B02, B03)
    }
}

sealed class CaptionType(val size: Int) {
    data object C01 : CaptionType(12)
    data object C02 : CaptionType(10)

    companion object {
        val entries = listOf(C01, C02)
    }
}

sealed class UnderlineType(val size: Int) {
    data object U01 : UnderlineType(14)
    data object U02 : UnderlineType(12)

    companion object { val entries = listOf(U01, U02) }
}

// -------------------------------------------------------------
// 3. Reusable StyleGroup for all typography types
// -------------------------------------------------------------
data class StyleGroup(
    val bold: TextStyle,
    val semiBold: TextStyle,
    val medium: TextStyle,
    val regular: TextStyle
)

data class HeadingTypography(
    val fontFamily: FontFamily,
    val styles: Map<HeadingType, StyleGroup>
)

data class BodyTypography(
    val fontFamily: FontFamily,
    val styles: Map<BodyType, StyleGroup>
)

data class CaptionTypography(
    val fontFamily: FontFamily,
    val styles: Map<CaptionType, StyleGroup>
)

data class UnderlineTypography(
    val fontFamily: FontFamily,
    val styles: Map<UnderlineType, StyleGroup>
)

// -------------------------------------------------------------
// 4. Factory: Create a StyleGroup based on size + font family
// -------------------------------------------------------------
fun createStyleGroup(
    size: Int,
    font: FontFamily,
    color: Color,
    underline: Boolean = false
): StyleGroup {

    val base = if (underline) TextDecoration.Underline else TextDecoration.None

    return StyleGroup(
        bold = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = font,
            color = color,
            textDecoration = base
        ),
        semiBold = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = font,
            color = color,
            textDecoration = base
        ),
        medium = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = font,
            color = color,
            textDecoration = base
        ),
        regular = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = font,
            color = color,
            textDecoration = base
        )
    )
}


// -------------------------------------------------------------
// 5. Factory functions: Auto-generate all styles
// -------------------------------------------------------------
fun createHeadingTypography(font: FontFamily, color: Color) =
    HeadingTypography(
        fontFamily = font,
        styles = HeadingType.entries.associateWith { type ->
            createStyleGroup(type.size, font, color)
        }
    )

fun createBodyTypography(font: FontFamily, color: Color) =
    BodyTypography(
        fontFamily = font,
        styles = BodyType.entries.associateWith { type ->
            createStyleGroup(type.size, font, color)
        }
    )

fun createCaptionTypography(font: FontFamily, color: Color) =
    CaptionTypography(
        fontFamily = font,
        styles = CaptionType.entries.associateWith { type ->
            createStyleGroup(type.size, font, color)
        }
    )


fun createUnderlineTypography(font: FontFamily, color: Color) =
    UnderlineTypography(
        fontFamily = font,
        styles = UnderlineType.entries.associateWith { type ->
            createStyleGroup(type.size, font, color, underline = true)
        }
    )


// -------------------------------------------------------------
// 6. Light Theme Typography Implementation
// -------------------------------------------------------------
class LightTypography : OMFTypography {

    private val defaultFont = FontFamily(
        Font(R.font.inter_light, FontWeight.Normal),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    override val heading = createHeadingTypography(defaultFont, Color.Black)

    override val body = createBodyTypography(defaultFont, Color.Black)

    override val caption = createCaptionTypography(defaultFont, Color.Gray)

    override val underline = createUnderlineTypography(defaultFont, Color.Black)

}

// -------------------------------------------------------------
// 7. Dark Theme Typography Implementation
// -------------------------------------------------------------
class DarkTypography : OMFTypography {

    private val defaultFont = FontFamily(
        Font(R.font.inter_light, FontWeight.Normal),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    override val heading = createHeadingTypography(defaultFont, Color.White)

    override val body = createBodyTypography(defaultFont, Color.White)

    override val caption = createCaptionTypography(defaultFont, Color.LightGray)

    override val underline = createUnderlineTypography(defaultFont, Color.Black)

}

