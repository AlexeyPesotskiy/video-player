package com.example.videoplayer.domain.repository

import com.example.videoplayer.domain.model.VideoInfo

interface VideoRepository {
    suspend fun getVideoList(): List<VideoInfo>
}