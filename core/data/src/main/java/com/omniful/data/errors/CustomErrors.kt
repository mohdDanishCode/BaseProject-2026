package com.omniful.data.errors


sealed interface AppError

sealed interface MovieError : AppError {
    data object NotFound : MovieError
    data object Database : MovieError
}


sealed interface Result<out T, out E> {
    data class Success<T>(val data: T) : Result<T, Nothing>
    data class Failure<E>(val error: E) : Result<Nothing, E>
}

fun MovieError.toUiMessage(): String = when (this) {
    MovieError.NotFound -> "Movie not available offline"
    MovieError.Database -> "Something went wrong"
}