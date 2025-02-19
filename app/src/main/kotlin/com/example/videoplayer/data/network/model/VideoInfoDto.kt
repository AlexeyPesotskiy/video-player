package com.example.videoplayer.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoInfoListDto(
    val items: MutableList<VideoInfoDto>,
)

@Serializable
data class VideoInfoDto(
    val id: String,
    val contentDetails: VideoContentDetailsDto,
    @SerialName("snippet")
    val info: VideoPreviewDto,
)

@Serializable
data class VideoContentDetailsDto(
    val duration: String,
)
/*
If < 1 minute - "PT#S"
If >= 1 minute long and < 1 hour long - PT#M#S
PT - period of time characters at M and S letters are integers.
PT15M33S - video is 15 minutes and 33 seconds long.

If >= 1 hour long - PT#H#M#S, in which at letter H the length in hours.
If >= 1 day long, the letters P and T are separated, and
the value 's format is P#DT#H#M#S.
*/

@Serializable
data class VideoPreviewDto(
    val title: String,
    val thumbnails: ThumbnailDto
)

@Serializable
data class ThumbnailDto(
    val medium: MediumThumbnailDto
)

@Serializable
data class MediumThumbnailDto(
    val url: String
)
