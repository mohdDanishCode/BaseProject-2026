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

@Singleton
class OmnifulNetworkFactory @Inject constructor(
    private val networkJson: Json,
    private val okhttpCallFactory: dagger.Lazy<Call.Factory>
) {
    private val cache = mutableMapOf<String, RetrofitOmnifulNetwork>()

    fun getNetworkForMerchant(merchant: String): RetrofitOmnifulNetwork {
        return cache.getOrPut(merchant) {
            val baseUrl = getBaseUrlForMerchant(merchant) // You need to define this logic.
            createNetwork(baseUrl)
        }
    }

    private fun createNetwork(baseUrl: String): RetrofitOmnifulNetwork {
        val network = RetrofitOmnifulNetwork(networkJson, okhttpCallFactory)
        network.initialize(baseUrl)
        return network
    }

    private fun getBaseUrlForMerchant(merchant: String): String {
        // Example logic for merchant-specific base URLs.
        return when (merchant) {
            "merchant1" -> "https://api.merchant1.com/"
            "merchant2" -> "https://api.merchant2.com/"
            else -> throw IllegalArgumentException("Unknown merchant: $merchant")
        }
    }
}
