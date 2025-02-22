package com.example.videoplayer.domain.repository

import com.example.videoplayer.domain.model.VideoInfo

interface VideoRepository {
    /**
     * Возвращает загруженный из сети список видео с информацией
     */
    suspend fun fetchVideoInfoList(): List<VideoInfo>

    /**
     * Возвращает кэшированный список видео с информацией
     */
    suspend fun getCachedVideoInfoList(): List<VideoInfo>
}
