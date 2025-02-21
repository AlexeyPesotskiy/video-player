package com.example.videoplayer.domain.repository

import com.example.videoplayer.domain.model.VideoInfo

interface VideoRepository {
    /**
     * Возвращает список видео с информацией (ссылка на видео, заголовок,
     * ссылка на заставку, длительность)
     */
    suspend fun getVideoInfoList(): List<VideoInfo>
}