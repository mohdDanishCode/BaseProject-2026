package com.omniful.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omniful.database.model.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {

    @Query("SELECT * FROM movie_remote_keys WHERE id = 0 LIMIT 1")
    suspend fun getLastRemoteKey(): MovieRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: MovieRemoteKeys)

    @Query("DELETE FROM movie_remote_keys")
    suspend fun clear()
}

