package com.example.videoplayer.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosDto(
    val categories: List<CategoryDto>,
)

@Serializable
data class CategoryDto(
    val videos: List<VideoInfoDto>,
)

@Serializable
data class VideoInfoDto(
    val sources: List<String>,
    @SerialName("thumb")
    val thumbLocation: String,
    val title: String,
)
