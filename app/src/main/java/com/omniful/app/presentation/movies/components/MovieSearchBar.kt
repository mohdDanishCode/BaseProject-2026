package com.omniful.app.presentation.movies.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalShadows
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.applyShadow

@Composable
fun MovieSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalOMFColors.current
    val typography = LocalTypography.current
    val size = LocalOMFSize.current
    val shadows = LocalShadows.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(size.spacing.spacing4)
            .applyShadow(
                style = shadows.theme.S01,
                cornerRadius = size.radius.radius4
            )
            .background(
                color = colors.surface,
                shape = RoundedCornerShape(size.radius.radius4)
            )
            .padding(horizontal = size.spacing.spacing4, vertical = size.spacing.spacing3)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = typography.body.styles[BodyType.B02]!!.regular,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = "Search movies",
                        style = typography.body.styles[BodyType.B02]!!.regular,
                        color = colors.secondary
                    )
                }
                innerTextField()
            }
        )
    }
}