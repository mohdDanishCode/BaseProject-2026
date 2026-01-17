package com.omniful.app.presentation.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.omniful.app.R
import com.omniful.app.presentation.movies.components.ErrorState
import com.omniful.app.presentation.movies.components.LoadingState
import com.omniful.designsystem.theme.Black100
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.HeadingType
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography

@Composable
fun MovieDetailScreen(
    movieId: Long,
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
                LoadingState()
            }
        }

        is MovieDetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                ErrorState(errorMessage = stringResource(R.string.movie_not_available_offline), onRetry = {
                    viewModel.loadMovie(movieId)
                })

            }
        }

        is MovieDetailUiState.Success -> {
            val movie = (state as MovieDetailUiState.Success).movie

            Column(
                modifier = Modifier
                    .fillMaxSize()
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = size.spacing.spacing4)
                ) {
                    Spacer(
                        modifier = Modifier.height(
                            size.spacing.spacing2
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
                            .background(Black100.copy(alpha = 0.1f)),
                    )

                    Spacer(
                        modifier = Modifier.height(
                            size.spacing.spacing6
                        )
                    )

                    Text(
                        text = movie.title,
                        style = typography.heading.styles[HeadingType.H06]!!.medium
                    )

                    Spacer(
                        modifier = Modifier.height(
                            size.spacing.spacing6
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
}

