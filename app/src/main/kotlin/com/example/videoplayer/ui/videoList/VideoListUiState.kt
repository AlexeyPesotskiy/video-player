package com.example.videoplayer.ui.videoList

import androidx.compose.runtime.Immutable
import com.example.videoplayer.domain.model.VideoInfo

@Immutable
data class VideoListUiState(
    val videoInfoList: List<VideoInfo>,
    val isLoading: Boolean,
    val isError: Boolean,
)