package com.example.videoplayer.fake

import com.example.videoplayer.data.local.VideoInfoEntity
import com.example.videoplayer.data.network.CategoryDto
import com.example.videoplayer.data.network.VideoInfoDto
import com.example.videoplayer.data.network.VideosDto

object FakeDataSource {
    const val videoDuration = "1:00"
    const val videoDurationInMs = "60000"
    private const val videoSrc1 = "http://url2.1"
    private const val thumbUrl1 = "http://url.2"
    private const val title1 = "http://url.3"
    private const val videoSrc2 = "http://url2.1"
    private const val thumbUrl2 = "http://url2.2"
    private const val title2 = "http://url2.3"

    private val videoInfo1 = VideoInfoDto(
        sources = listOf(videoSrc1),
        thumbLocation = thumbUrl1,
        title = title1
    )
    private val videoInfo2 = VideoInfoDto(
        sources = listOf(videoSrc2),
        thumbLocation = thumbUrl2,
        title = title2
    )

    private val videoInfoEntity1 = VideoInfoEntity(
        videoUrl = videoSrc1,
        title = title1,
        thumbnailUrl = thumbUrl1,
        duration = videoDuration
    )
    private val videoInfoEntity2 = VideoInfoEntity(
        videoUrl = videoSrc2,
        title = title2,
        thumbnailUrl = thumbUrl2,
        duration = videoDuration
    )


    private val category = CategoryDto(
        videos = listOf(
            videoInfo1,
            videoInfo2
        )
    )

    val videosDto = VideosDto(
        categories = listOf(category)
    )

    val videoInfoEntities = listOf(
        videoInfoEntity1,
        videoInfoEntity2
    )
}
