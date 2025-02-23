package com.example.videoplayer.fake

import com.example.videoplayer.data.network.VideoApiService
import com.example.videoplayer.data.network.VideosDto

class FakeVideoApiService: VideoApiService {
    override suspend fun fetchVideos(): VideosDto =
        FakeDataSource.videosDto
}