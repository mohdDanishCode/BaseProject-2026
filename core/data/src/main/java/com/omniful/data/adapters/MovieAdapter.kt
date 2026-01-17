package com.omniful.data.adapters

import com.omniful.data.model.movies.MovieUiModel
import com.omniful.database.model.MovieEntity
import com.omniful.network.model.movie.Movie
import com.omniful.network.model.movie.TmdbImageConfig

fun MovieEntity.toUiModel(): MovieUiModel {
    return MovieUiModel(
        id = id,
        title = title.orEmpty(),
        posterUrl = TmdbImageConfig.posterUrl(posterPath ?: ""),
        overview = overview.orEmpty(),
        fullPosterUrl =TmdbImageConfig.posterUrl(posterPath ?: "", size = TmdbImageConfig.Size.ORIGINAL)
    )
}

fun Movie.toEntity(page: Int): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        page=page
    )
}