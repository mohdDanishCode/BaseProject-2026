package com.omniful.app.presentation.movies.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography

@Composable
fun EmptyState() {
    Text(
        text = "No movies found",
        style = LocalTypography.current.body.styles[BodyType.B02]!!.regular,
        modifier = Modifier.padding(LocalOMFSize.current.spacing.spacing6)
    )
}