package com.omniful.app.presentation.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.HeadingType
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.OMFTypography
import com.omniful.designsystem.theme.OmnifulTheme

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val size = LocalOMFSize.current
    val typography = LocalTypography.current

    when (state) {
        is MovieDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MovieDetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Movie not available offline",
                    style = typography.body.styles[BodyType.B02]!!.regular
                )
            }
        }

        is MovieDetailUiState.Success -> {
            val movie = (state as MovieDetailUiState.Success).movie

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(size.spacing.spacing4)
            ) {

                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        size.spacing.spacing3
                    )
                )

                AsyncImage(
                    model = movie.fullPosterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f)
                        .clip(
                            RoundedCornerShape(
                                size.radius.radius4
                            )
                        )
                )

                Spacer(
                    modifier = Modifier.height(
                        size.spacing.spacing4
                    )
                )

                Text(
                    text = movie.title,
                    style = typography.heading.styles[HeadingType.H05]!!.bold
                )

                Spacer(
                    modifier = Modifier.height(
                        size.spacing.spacing3
                    )
                )

                Text(
                    text = movie.overview,
                    style = typography.body.styles[BodyType.B01]!!.regular
                )
            }
        }
    }
}

