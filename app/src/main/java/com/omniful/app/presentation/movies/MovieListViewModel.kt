package com.omniful.app.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omniful.data.model.movies.MovieUiModel
import com.omniful.data.repository.movies.MovieRepository
import com.omniful.data.repository.movies.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel@Inject constructor(
    private val repository: MovieRepositoryImpl
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    data class UiState(
        val searchQuery: String = ""
    )

    val uiState: StateFlow<UiState> = searchQuery
        .map { UiState(searchQuery = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState()
        )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<MovieUiModel>> =
        searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    repository.getTrendingMovies()
                } else {
                    repository.searchMovies(query)
                }
            }
            .cachedIn(viewModelScope)


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
