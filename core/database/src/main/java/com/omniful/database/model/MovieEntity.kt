package com.omniful.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies",
    indices = [
        Index(value = ["title"]),
        Index(value = ["page"])
    ]
)
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val page: Int,
    val cachedAt: Long = System.currentTimeMillis()
)