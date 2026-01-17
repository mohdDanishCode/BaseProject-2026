package com.omniful.app.presentation.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.omniful.app.presentation.movies.components.EmptyState
import com.omniful.app.presentation.movies.components.ErrorState
import com.omniful.app.presentation.movies.components.LoadingState
import com.omniful.app.presentation.movies.components.MovieCard
import com.omniful.app.presentation.movies.components.MovieSearchBar
import com.omniful.designsystem.theme.LocalOMFSize

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














