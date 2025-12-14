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
    val bodyUnderline: BodyUnderlineTypography

    val captionUnderline: CaptionUnderlineTypography
}

// -------------------------------------------------------------
// 2. Type Definitions (Heading / Body / Caption)
// -------------------------------------------------------------
sealed class HeadingType(val size: Int,val lineHeight: Int) {
    data object H01 : HeadingType(64,72)
    data object H02 : HeadingType(56,64)
    data object H03 : HeadingType(48,56)
    data object H04 : HeadingType(40,48)

    data object H05 : HeadingType(32,40)

    data object H06 : HeadingType(24,32)

    companion object {
        val entries = listOf(H01, H02, H03, H04, H05, H06)
    }
}

sealed class BodyType(val size: Int,val lineHeight: Int) {
    data object B01 : BodyType(16,24)
    data object B02 : BodyType(14,24)
    data object B03 : BodyType(14,20)

    companion object {
        val entries = listOf(B01, B02, B03)
    }
}

sealed class CaptionType(val size: Int,val lineHeight: Int) {
    data object C01 : CaptionType(12,16)
    data object C02 : CaptionType(10,12)

    companion object {
        val entries = listOf(C01, C02)
    }
}

sealed class CaptionUnderlineType(val size: Int,val lineHeight: Int) {
    data object C01 : CaptionType(12,16)
    data object C02 : CaptionType(10,12)

    companion object {
        val entries = listOf(C01, C02)
    }
}

sealed class BodyUnderlineType(val size: Int,val lineHeight: Int) {
    data object U01 : BodyUnderlineType(16,24)
    data object U02 : BodyUnderlineType(14,24)

    data object U03 : BodyUnderlineType(14,20)

    companion object { val entries = listOf(U01, U02, U03) }
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

data class CaptionUnderlineTypography(
    val fontFamily: FontFamily,
    val styles: Map<CaptionType, StyleGroup>
)

data class BodyUnderlineTypography(
    val fontFamily: FontFamily,
    val styles: Map<BodyUnderlineType, StyleGroup>
)

// -------------------------------------------------------------
// 4. Factory: Create a StyleGroup based on size + font family
// -------------------------------------------------------------
fun createStyleGroup(
    size: Int,
    font: FontFamily,
    color: Color,
    lineHeight: Int,
    underline: Boolean = false
): StyleGroup {

    val base = if (underline) TextDecoration.Underline else TextDecoration.None

    return StyleGroup(
        bold = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = font,
            color = color,
            textDecoration = base,
            lineHeight = lineHeight.sp
        ),
        semiBold = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = font,
            color = color,
            textDecoration = base,
            lineHeight = lineHeight.sp
        ),
        medium = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = font,
            color = color,
            textDecoration = base,
            lineHeight = lineHeight.sp
        ),
        regular = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = font,
            color = color,
            textDecoration = base,
            lineHeight = lineHeight.sp
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
            createStyleGroup(type.size, font, color,type.lineHeight)
        }
    )

fun createBodyTypography(font: FontFamily, color: Color) =
    BodyTypography(
        fontFamily = font,
        styles = BodyType.entries.associateWith { type ->
            createStyleGroup(type.size, font, color,type.lineHeight)
        }
    )

fun createCaptionTypography(font: FontFamily, color: Color) =
    CaptionTypography(
        fontFamily = font,
        styles = CaptionType.entries.associateWith { type ->
            createStyleGroup(type.size, font, color,type.lineHeight)
        }
    )


fun createBodyUnderlineTypography(font: FontFamily, color: Color) =
    BodyUnderlineTypography(
        fontFamily = font,
        styles = BodyUnderlineType.entries.associateWith { type ->
            createStyleGroup(type.size, font, color,type.lineHeight, underline = true)
        }
    )

fun createCaptionUnderlineTypography(font: FontFamily, color: Color) =
    CaptionUnderlineTypography(
        fontFamily = font,
        styles = CaptionUnderlineType.entries.associateWith { type ->
            createStyleGroup(type.size, font, color,type.lineHeight, underline = true)
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

    override val bodyUnderline = createBodyUnderlineTypography(defaultFont, Color.Black)

    override val captionUnderline = createCaptionUnderlineTypography(defaultFont, Color.Black)


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

    override val caption = createCaptionTypography(defaultFont, Color.Gray)

    override val bodyUnderline = createBodyUnderlineTypography(defaultFont, Color.Black)

    override val captionUnderline = createCaptionUnderlineTypography(defaultFont, Color.Black)

}

