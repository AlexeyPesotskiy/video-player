package com.example.videoplayer.data.mapper

import com.example.videoplayer.data.local.VideoInfoEntity
import com.example.videoplayer.data.network.VideoInfoDto
import com.example.videoplayer.data.utils.msToDdHhMmSs
import com.example.videoplayer.domain.model.VideoInfo
import javax.inject.Inject
import javax.inject.Named

class VideoInfoMappers @Inject constructor(
    @Named("BASE_URL")
    val baseUrl: String,
) {
    fun mapToDomain(videoInfoDto: VideoInfoDto, duration: Long): VideoInfo =
        VideoInfo(
            videoUrl = videoInfoDto.sources[0],
            title = videoInfoDto.title,
            thumbnailUrl = baseUrl + videoInfoDto.thumbLocation,
            duration = duration.msToDdHhMmSs()
        )

    fun mapToDomain(videoInfoEntity: VideoInfoEntity): VideoInfo = with(videoInfoEntity) {
        VideoInfo(
            videoUrl = videoUrl,
            title = title,
            thumbnailUrl = thumbnailUrl,
            duration = duration
        )
    }

    fun mapToEntity(videoInfo: VideoInfo): VideoInfoEntity = with(videoInfo) {
        VideoInfoEntity(
            videoUrl = videoUrl,
            title = title,
            thumbnailUrl = thumbnailUrl,
            duration = duration
        )
    }
}
