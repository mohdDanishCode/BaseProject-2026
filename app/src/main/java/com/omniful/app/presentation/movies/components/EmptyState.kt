package com.omniful.app.presentation.movies.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.omniful.app.R
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography

@Composable
fun EmptyState(modifier:Modifier,message: String = stringResource(R.string.no_movies_found)) {
    Text(
        text = message,
        style = LocalTypography.current.body.styles[BodyType.B02]!!.regular,
        modifier = modifier.padding(LocalOMFSize.current.spacing.spacing6),
        textAlign = TextAlign.Center
    )
}