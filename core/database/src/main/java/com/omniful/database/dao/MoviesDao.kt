package com.omniful.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omniful.database.model.MovieEntity

@Dao
interface MovieDao {

    // For network-based pagination
    @Query("SELECT * FROM movies WHERE page = :page ORDER BY id ASC")
    suspend fun getMoviesByPage(page: Int): List<MovieEntity>

    // PagingSource for local DB search
    @Query("""
        SELECT * FROM movies 
        WHERE title LIKE '%' || :query || '%'
        ORDER BY page ASC, id ASC
    """)
    fun searchPagingSource(query: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Long): MovieEntity?

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies WHERE page = :page")
    suspend fun deleteByPage(page: Int)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
