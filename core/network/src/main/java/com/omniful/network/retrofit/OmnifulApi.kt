package com.omniful.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.omniful.network.BuildConfig
import com.omniful.network.model.Product
import com.omniful.network.model.movie.MovieListResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

sealed class RemoteUrl(
    val baseUrl: String
) {
    data object tmdbBaseUrl : RemoteUrl("https://api.themoviedb.org")
}
interface TmdbNetworkApi {

    @GET("/3/trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MovieListResponse
}

@Singleton
class ApiProvider @Inject constructor(
    private val json: Json,
    private val okHttpClient: OkHttpClient
) {
    private val cache = ConcurrentHashMap<RemoteUrl, TmdbNetworkApi>()

    fun apiFor(merchant: RemoteUrl): TmdbNetworkApi {
        return cache.computeIfAbsent(merchant) {
            Retrofit.Builder()
                .baseUrl(it.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(
                    json.asConverterFactory("application/json".toMediaType())
                )
                .build()
                .create(TmdbNetworkApi::class.java)
        }
    }
}




