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
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(): String =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/"

    @Provides
    fun provideRetrofit(
        @Named("BASE_URL")
        baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideVideoApiService(
        retrofit: Retrofit,
    ): VideoApiService = retrofit.create(VideoApiService::class.java)
}