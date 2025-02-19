package com.example.videoplayer.data.network

import com.example.videoplayer.data.network.model.VideoInfoListDto
import retrofit2.http.GET

interface VideoApiService {

    @GET("videos?part=snippet, contentDetails&maxResults=50&chart=mostPopular&key=$API_KEY")
    suspend fun fetchVideosList(): VideoInfoListDto

    companion object {
        private const val API_KEY = "AIzaSyCDlttZLBvTTIafUFCoM8LUrBHX0ltk7TA"
    }
}
