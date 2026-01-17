package com.omniful.app.presentation.movieDetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omniful.common.network.Dispatcher
import com.omniful.common.network.OmnifulDispatchers
import com.omniful.data.errors.MovieError
import com.omniful.data.errors.toUiMessage
import com.omniful.data.model.movies.MovieUiModel
import com.omniful.data.repository.movies.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
    private val repository: MovieRepositoryImpl,
    @Dispatcher(OmnifulDispatchers.IO)  private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()


    fun loadMovie(movieId: Long) {
        viewModelScope.launch(dispatcher) {
            _uiState.value = MovieDetailUiState.Loading

            when (val result = repository.getMovieById(movieId)) {
                is com.omniful.data.errors.Result.Success -> {
                    _uiState.value = MovieDetailUiState.Success(result.data)
                }

                is com.omniful.data.errors.Result.Failure -> {
                    _uiState.value = MovieDetailUiState.Error(
                        message = result.error.toUiMessage()
                    )
                }
            }
        }
    }
}
