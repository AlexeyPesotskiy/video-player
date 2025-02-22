package com.example.videoplayer.data.repository

import com.example.videoplayer.data.local.VideoInfoDao
import com.example.videoplayer.data.mapper.VideoInfoMappers
import com.example.videoplayer.data.network.VideoApiService
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.domain.repository.VideoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import wseemann.media.FFmpegMediaMetadataRetriever
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApiService: VideoApiService,
    private val videoInfoMappers: VideoInfoMappers,
    private val videoInfoDao: VideoInfoDao,
) : VideoRepository {

    override suspend fun fetchVideoInfoList(): List<VideoInfo> {
        val videoInfoList = fetchAndMapVideos()
        updateDatabase(videoInfoList)
        return videoInfoList
    }

    override suspend fun getCachedVideoInfoList(): List<VideoInfo> =
        videoInfoDao.getAllVideos().map {
            videoInfoMappers.mapToDomain(it)
        }

    private suspend fun fetchAndMapVideos(): List<VideoInfo> = coroutineScope {
        videoApiService.fetchVideos().categories[0].videos.map {
            async {
                val videoDuration = getVideoDuration(it.sources[0])
                videoInfoMappers.mapToDomain(it, videoDuration)
            }
        }.awaitAll()
    }

    private suspend fun updateDatabase(videoInfoList: List<VideoInfo>) {
        val videoEntityList = videoInfoList.map { element ->
            videoInfoMappers.mapToEntity(element)
        }
        videoInfoDao.deleteOldVideos(videoEntityList.map {
            it.videoUrl
        })
        videoInfoDao.insertVideos(videoEntityList)
    }

    private fun getVideoDuration(videoUrl: String): Long {
        val retriever = FFmpegMediaMetadataRetriever()
        return try {
            retriever.setDataSource(videoUrl)
            val durationStr = retriever
                .extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION)
            durationStr?.toLong() ?: 0L
        } catch (e: Exception) {
            0L
        } finally {
            retriever.release()
        }
    }
}
