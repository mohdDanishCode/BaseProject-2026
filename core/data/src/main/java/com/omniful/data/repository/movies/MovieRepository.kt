package com.omniful.data.repository.movies

import androidx.paging.PagingData
import com.omniful.data.model.movies.MovieUiModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingMovies(): Flow<PagingData<MovieUiModel>>
    fun searchMovies(query: String): Flow<PagingData<MovieUiModel>>
    suspend fun getMovieById(id: Long): MovieUiModel?

}