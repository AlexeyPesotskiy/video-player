package com.example.videoplayer.domain.repository

import com.example.videoplayer.domain.model.VideoInfo
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    /**
     * Возвращает загруженный из сети список видео с информацией
     */
    suspend fun fetchVideoInfoFlow(): Flow<VideoInfo>

    /**
     * Возвращает кэшированный список видео с информацией
     */
    suspend fun getCachedVideoInfoList(): List<VideoInfo>
}
