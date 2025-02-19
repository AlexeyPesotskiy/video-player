package com.example.videoplayer.data.di

import com.example.videoplayer.data.network.VideoApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()

    @Provides
    fun provideVideoApiService(
        retrofit: Retrofit,
    ): VideoApiService = retrofit.create(VideoApiService::class.java)

    private val json = Json {
        ignoreUnknownKeys = true
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    }
}