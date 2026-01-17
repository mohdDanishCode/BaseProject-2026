package com.omniful.network.model.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?
)

object TmdbImageConfig {

    private const val BASE_URL = "https://image.tmdb.org/t/p/"

    enum class Size(val value: String) {
        ORIGINAL("original"),
        W500("w500")
    }

    fun posterUrl(
        path: String,
        size: Size = Size.W500
    ): String = "$BASE_URL${size.value}$path"
}