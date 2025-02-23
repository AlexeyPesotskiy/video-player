package com.example.videoplayer.data.repository

import com.example.videoplayer.data.local.VideoInfoDao
import com.example.videoplayer.data.mapper.VideoInfoMappers
import com.example.videoplayer.data.network.VideoApiService
import com.example.videoplayer.data.network.mediaMetadataRetriever.FFmpegMediaMetadataRetrieverFactory
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.domain.repository.VideoRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wseemann.media.FFmpegMediaMetadataRetriever
import java.util.LinkedList
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApiService: VideoApiService,
    private val videoInfoMappers: VideoInfoMappers,
    private val videoInfoDao: VideoInfoDao,
    private val retrieverFactory: FFmpegMediaMetadataRetrieverFactory,
) : VideoRepository {

    override suspend fun fetchVideoInfoFlow(): Flow<VideoInfo> = channelFlow {
        val videoInfoList = LinkedList<VideoInfo>()
        try {
            fetchAndMapVideosInFlow().collect {
                videoInfoList.push(it)
                send(it)
            }
        } finally {
            withContext(NonCancellable) {
                if (videoInfoList.isNotEmpty()) {
                    updateDatabase(videoInfoList)
                }
            }
        }
    }

    override suspend fun getCachedVideoInfoList(): List<VideoInfo> =
        videoInfoDao.getAllVideos().map {
            videoInfoMappers.mapToDomain(it)
        }


    private fun fetchAndMapVideosInFlow(): Flow<VideoInfo> = channelFlow {
        videoApiService.fetchVideos().categories[0].videos.map {
            coroutineScope {
                launch {
                    val videoDuration = getVideoDuration(it.sources[0])
                    send(videoInfoMappers.mapToDomain(it, videoDuration))
                }
            }
        }
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
        val retriever = retrieverFactory.create()
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
