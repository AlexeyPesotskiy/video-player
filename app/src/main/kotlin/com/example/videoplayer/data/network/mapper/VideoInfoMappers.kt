package com.example.videoplayer.data.network.mapper

import com.example.videoplayer.data.network.model.VideoInfoDto
import com.example.videoplayer.data.utils.formatDuration
import com.example.videoplayer.domain.model.VideoInfo
import javax.inject.Inject

class VideoInfoMappers @Inject constructor() {
    fun mapToDomain(videoInfoDto: VideoInfoDto): VideoInfo =
        VideoInfo(
            videoId = videoInfoDto.id,
            title = videoInfoDto.info.title,
            thumbnailUrl = videoInfoDto.info.thumbnails.medium.url,
            duration = videoInfoDto.contentDetails.duration.formatDuration()
        )
}