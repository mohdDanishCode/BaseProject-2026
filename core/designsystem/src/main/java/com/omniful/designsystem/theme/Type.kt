package com.omniful.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp



interface OMFTypography{
    val heading: TextStyle
    val body: TextStyle
    val caption: TextStyle
}


data class DefaultTypography(
    override val heading: TextStyle=TextStyle.Default,
    override val body: TextStyle=TextStyle.Default,
    override val caption: TextStyle =TextStyle.Default
) : OMFTypography{

}

data class LightTypography(
    override val heading: TextStyle =TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    ),
    override val body: TextStyle =TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.DarkGray
    ),
    override val caption: TextStyle=TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = Color.Gray
    )
): OMFTypography





data class DarkTypography(
    override val heading: TextStyle =TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    ),
    override val body: TextStyle  = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.LightGray
    ),
    override val caption: TextStyle=TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = Color.Gray
    )
): OMFTypography