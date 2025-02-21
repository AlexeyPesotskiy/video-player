package com.example.videoplayer.data.repository

import android.media.MediaMetadataRetriever
import com.example.videoplayer.data.network.VideoApiService
import com.example.videoplayer.data.network.mapper.VideoInfoMappers
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.domain.repository.VideoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApiService: VideoApiService,
    private val videoInfoMappers: VideoInfoMappers,
) : VideoRepository {

    override suspend fun getVideoInfoList(): List<VideoInfo> = coroutineScope {
        videoApiService.fetchVideos().categories[0].videos.map {
            async {
                val videoDuration = getVideoDuration(it.sources[0])
                videoInfoMappers.mapToDomain(it, videoDuration)
            }
        }.awaitAll()
    }

    private fun getVideoDuration(videoUrl: String): Long {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(videoUrl)
            val durationStr = retriever
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            durationStr?.toLong() ?: 0L
        } catch (e: Exception) {
            0L
        } finally {
            retriever.release()
        }
    }
}
