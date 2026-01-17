/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omniful.network.di

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil3.ImageLoader
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.svg.SvgDecoder
import com.omniful.network.BuildConfig
import com.omniful.network.retrofit.ApiKeyInterceptor
import com.omniful.network.retrofit.ApiProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val apiKeyInterceptor = ApiKeyInterceptor(
            apiKey = BuildConfig.TMDB_API_KEY
        )

        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideCallFactory(
        okHttpClient: OkHttpClient
    ): Call.Factory = okHttpClient


    @Provides
    @Singleton
    fun provideOmnifulApiProvider(
        json: Json,
        okHttpClient: OkHttpClient
    ): ApiProvider =
        ApiProvider(json, okHttpClient)

    @Provides
    @Singleton
    fun imageLoader(
        // We specifically request dagger.Lazy here, so that it's not instantiated from Dagger.
        okHttpCallFactory: dagger.Lazy<Call.Factory>,
        @ApplicationContext application: Context,
    ): ImageLoader = ImageLoader.Builder(application)
        .components {
            add(OkHttpNetworkFetcherFactory(callFactory = {
                okHttpCallFactory.get()
            }))
            add(SvgDecoder.Factory())
            add(if (SDK_INT >= 28) {
                AnimatedImageDecoder.Factory()
            } else {
                GifDecoder.Factory()
            })
        }

        .apply {
//            if (BuildConfig.DEBUG) {
//                logger(DebugLogger())
//            }
        }
        .build()


}
