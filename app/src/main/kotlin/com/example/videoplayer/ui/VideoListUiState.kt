package com.example.videoplayer.ui

import androidx.compose.runtime.Immutable
import com.example.videoplayer.domain.model.VideoInfo

@Immutable
sealed interface VideoListUiState {
    data class Success(
        val videoInfoList: List<VideoInfo>,
    ) : VideoListUiState

    data object Loading : VideoListUiState
    data object Error : VideoListUiState
}