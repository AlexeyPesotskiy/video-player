package com.example.videoplayer.domain.usecase

import com.example.videoplayer.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideoInfoListUseCase @Inject constructor(
    private val videoRepository: VideoRepository,
) {
    suspend fun execute() = videoRepository.getVideoList()
}