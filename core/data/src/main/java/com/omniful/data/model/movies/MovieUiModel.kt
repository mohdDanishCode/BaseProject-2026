package com.omniful.data.model.movies

import androidx.compose.runtime.Immutable

@Immutable
data class MovieUiModel(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val fullPosterUrl: String,
    val overview: String
)
