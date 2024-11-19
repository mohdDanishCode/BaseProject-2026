package com.omniful.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class OMFTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val caption: TextStyle
)

val LightTypography = OMFTypography(
    heading = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    ),
    body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.DarkGray
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = Color.Gray
    )
)

val DarkTypography = OMFTypography(
    heading = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    ),
    body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.LightGray
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = Color.Gray
    )
)