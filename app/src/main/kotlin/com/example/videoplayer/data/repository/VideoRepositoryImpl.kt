package com.example.videoplayer.data.repository

import com.example.videoplayer.data.network.VideoApiService
import com.example.videoplayer.data.network.mapper.VideoInfoMappers
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApiService: VideoApiService,
    private val videoInfoMappers: VideoInfoMappers,
) : VideoRepository {
    override suspend fun getVideoList(): List<VideoInfo> =
        videoApiService.fetchVideos().categories[0].videos.map {
            videoInfoMappers.mapToDomain(it)
        }
}
