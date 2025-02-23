package com.example.videoplayer.data.network

import retrofit2.http.GET

interface VideoApiService {

    @GET("videos.json")
    suspend fun fetchVideos(): VideosDto
}
