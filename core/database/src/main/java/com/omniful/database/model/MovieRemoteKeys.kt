package com.omniful.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_keys")
data class MovieRemoteKeys(
    @PrimaryKey val id: Long = 0,
    val nextPage: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)
