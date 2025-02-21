package com.example.videoplayer.data.network.mapper

import com.example.videoplayer.data.network.model.VideoInfoDto
import com.example.videoplayer.data.utils.msToDdHhMmSs
import com.example.videoplayer.domain.model.VideoInfo
import javax.inject.Inject
import javax.inject.Named

class VideoInfoMappers @Inject constructor(
    @Named("BASE_URL")
    val baseUrl: String
) {
    fun mapToDomain(videoInfoDto: VideoInfoDto, duration: Long): VideoInfo =
        VideoInfo(
            videoUrl = videoInfoDto.sources[0],
            title = videoInfoDto.title,
            thumbnailUrl = baseUrl + videoInfoDto.thumbLocation,
            duration = duration.msToDdHhMmSs()
        )
}