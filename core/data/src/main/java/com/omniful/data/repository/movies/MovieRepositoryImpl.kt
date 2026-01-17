package com.omniful.data.repository.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.omniful.data.adapters.toUiModel
import com.omniful.data.errors.MovieError
import com.omniful.data.model.movies.MovieUiModel
import com.omniful.database.OmnifulDatabase
import com.omniful.database.dao.MovieDao
import com.omniful.network.retrofit.ApiProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val apiProvider: ApiProvider,
    private val dao: MovieDao,
    private val database: OmnifulDatabase
) : MovieRepository {

    override fun getTrendingMovies(): Flow<PagingData<MovieUiModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiProvider, dao)
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toUiModel()
            }
        }
    }

    override fun searchMovies(query: String): Flow<PagingData<MovieUiModel>> {
        // Search directly from DB using Room's PagingSource
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                dao.searchPagingSource(query)
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toUiModel()
            }
        }
    }

    override suspend fun getMovieById(
        id: Long
    ): com.omniful.data.errors.Result<MovieUiModel, MovieError> {
        return try {
            val entity = dao.getMovieById(id)

            if (entity != null) {
                com.omniful.data.errors.Result.Success(entity.toUiModel())
            } else {
                com.omniful.data.errors.Result.Failure(MovieError.NotFound)
            }
        } catch (e: Exception) {
            com.omniful.data.errors.Result.Failure(MovieError.Database)
        }
    }
}
