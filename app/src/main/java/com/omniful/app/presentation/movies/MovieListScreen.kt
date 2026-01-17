package com.omniful.app.presentation.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.omniful.data.model.movies.MovieUiModel
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.BodyUnderlineType
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalShadows
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.applyShadow

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel=hiltViewModel(),
    onMovieClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()

    val size = LocalOMFSize.current

    Column(modifier = Modifier.fillMaxSize()) {

        MovieSearchBar(
            value = uiState.searchQuery,
            onValueChange = viewModel::onSearchQueryChanged
        )

        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            modifier =Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(size.spacing.spacing4),
            horizontalArrangement = Arrangement.spacedBy(size.spacing.spacing4),
            verticalArrangement = Arrangement.spacedBy(size.spacing.spacing4)
        ) {

            items(movies.itemCount, key = { index -> movies[index]?.id ?: index }) { index ->
                movies[index]?.let { movie ->
                    MovieCard(
                        movie = movie,
                        onClick = { onMovieClick(movie.id) }
                    )
                }
            }

            // -------- Paging States --------
            movies.apply {

                when {
                    loadState.refresh is LoadState.Loading -> {
                        item(span = { GridItemSpan(2) }) {
                            LoadingState()
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item(span = { GridItemSpan(2) }) { LoadingState() }
                    }
                    loadState.append is LoadState.Error -> {
                        item(span = { GridItemSpan(2) }) {
                            ErrorState(onRetry = { retry() })
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        item(span = { GridItemSpan(2) }) {
                            ErrorState(onRetry = { retry() })
                        }
                    }

                    itemCount == 0 && loadState.refresh is LoadState.NotLoading -> {
                        item(span = { GridItemSpan(2) }) {
                            EmptyState()
                        }
                    }
                }
            }
        }
    }
}

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


@Composable
fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalOMFSize.current.spacing.spacing6),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun EmptyState() {
    Text(
        text = "No movies found",
        style = LocalTypography.current.body.styles[BodyType.B02]!!.regular,
        modifier = Modifier.padding(LocalOMFSize.current.spacing.spacing6)
    )
}


@Composable
fun ErrorState(onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(LocalOMFSize.current.spacing.spacing6)
    ) {
        Text(
            text = "Something went wrong",
            style = LocalTypography.current.body.styles[BodyType.B02]!!.medium
        )

        Spacer(modifier = Modifier.height(LocalOMFSize.current.spacing.spacing3))

        Text(
            text = "Tap to retry",
            modifier = Modifier.clickable(onClick = onRetry),
            style = LocalTypography.current.bodyUnderline.styles[BodyUnderlineType.U02]!!.medium
        )
    }
}
