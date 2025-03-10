package com.example.videoplayer.domain.usecase

import com.example.videoplayer.domain.repository.VideoRepository
import javax.inject.Inject

class GetCachedVideoInfoListUseCase @Inject constructor(
    private val videoRepository: VideoRepository,
) {
    suspend fun execute() = videoRepository.getCachedVideoInfoList()
}
