package com.omniful.app.presentation.movies


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omniful.data.model.movies.MovieUiModel
import com.omniful.data.repository.movies.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MovieDetailUiState {
    object Loading : MovieDetailUiState
    data class Success(val movie: MovieUiModel) : MovieDetailUiState
    data class Error(val message: String) : MovieDetailUiState
}
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepositoryImpl
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    fun loadMovie(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movie = repository.getMovieById(movieId)?:throw Exception("No movie found")
                _uiState.value = MovieDetailUiState.Success(movie)
            } catch (e: Exception) {
                _uiState.value =
                    MovieDetailUiState.Error("Movie not available offline")
            }
        }
    }
}
