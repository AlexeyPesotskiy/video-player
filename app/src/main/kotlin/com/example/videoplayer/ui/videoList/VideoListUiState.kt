package com.example.videoplayer.ui.videoList

import com.example.videoplayer.domain.model.VideoInfo
import java.util.LinkedList

data class VideoListUiState(
    val videoInfoList: LinkedList<VideoInfo>,
    val isLoading: Boolean,
    val isError: Boolean,
) {
    override fun equals(other: Any?): Boolean =
        if (other is VideoListUiState)
            videoInfoList.size == other.videoInfoList.size && super.equals(other)
        else
            super.equals(other)

    override fun hashCode(): Int {
        var result = videoInfoList.hashCode()
        result = 31 * result + isLoading.hashCode()
        result = 31 * result + isError.hashCode()
        return result
    }
}
