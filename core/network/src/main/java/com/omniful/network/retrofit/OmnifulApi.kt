package com.omniful.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.omniful.network.model.Product
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton


interface OmnifulNetworkApi {
     @GET(value = "/objects")
     suspend fun getProducts(): List<Product>
}



@Singleton
class RetrofitOmnifulNetwork @Inject constructor(
    private val networkJson: Json,
    private val okhttpCallFactory: dagger.Lazy<Call.Factory>
)  {

    var networkApi: OmnifulNetworkApi? = null

    // should be initialized before call
    fun initialize(baseUrl: String) {
        networkApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(OmnifulNetworkApi::class.java)
    }
}
