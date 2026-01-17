package com.omniful.app.presentation.movies.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import com.omniful.data.model.movies.MovieUiModel
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalShadows
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.applyShadow

@Composable
fun MovieCard(
    movie: MovieUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalTypography.current
    val size = LocalOMFSize.current
    val shadows = LocalShadows.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .applyShadow(
                style = shadows.theme.S02,
                cornerRadius = size.radius.radius3
            )
            .background(
                color = LocalOMFColors.current.surface,
                shape = RoundedCornerShape(size.radius.radius3)
            )
            .clickable(onClick = onClick)
            .padding(size.spacing.spacing3)
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(size.radius.radius3))
                .background(LocalOMFColors.current.secondary.copy(alpha = 0.1f))
        )

        Spacer(modifier = Modifier.height(size.spacing.spacing2))

        Text(
            text = movie.title,
            style = typography.body.styles[BodyType.B02]!!.medium,
            maxLines = 2
        )
    }
}