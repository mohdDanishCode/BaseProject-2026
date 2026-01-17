package com.omniful.data.repository.movies

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omniful.data.adapters.toEntity
import com.omniful.database.dao.MovieDao
import com.omniful.database.model.MovieEntity
import com.omniful.network.retrofit.ApiProvider
import com.omniful.network.retrofit.RemoteUrl

class MoviePagingSource(
    private val apiProvider: ApiProvider,
    private val dao: MovieDao
) : PagingSource<Int, MovieEntity>() {

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val page = params.key ?: 1

        return try {
            Log.d("PagingSource", "🌐 Fetching page $page from API")

            // Fetch from network
            val response = apiProvider.apiFor(RemoteUrl.tmdbBaseUrl)
                .getTrendingMovies(page = page)

            // Convert to entities with page number
            val entities = response.results.map { it.toEntity(page = page) }

            // Clear old data for this page and insert new data
            dao.deleteByPage(page)
            dao.insertAll(entities)

            Log.d("PagingSource", "💾 Saved ${entities.size} items for page $page to DB")

            // Return from DB to ensure consistency
            val moviesFromDb = dao.getMoviesByPage(page)

            LoadResult.Page(
                data = moviesFromDb,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page >= response.totalPages) null else page + 1
            )

        } catch (e: Exception) {
            Log.e("PagingSource", "❌ Network error: ${e.message}")

            // On error, try to load from DB
            val cachedMovies = dao.getMoviesByPage(page)

            if (cachedMovies.isNotEmpty()) {
                Log.d("PagingSource", "📂 Returning ${cachedMovies.size} cached items for page $page")
                LoadResult.Page(
                    data = cachedMovies,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = page + 1
                )
            } else {
                Log.e("PagingSource", "💥 No cached data available")
                LoadResult.Error(e)
            }
        }
    }
}