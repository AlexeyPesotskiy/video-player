package com.example.videoplayer

import com.example.videoplayer.data.mapper.VideoInfoMappers
import com.example.videoplayer.data.network.mediaMetadataRetriever.FFmpegMediaMetadataRetrieverFactory
import com.example.videoplayer.data.network.mediaMetadataRetriever.FFmpegMediaMetadataRetrieverWrapper
import com.example.videoplayer.data.repository.VideoRepositoryImpl
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.domain.repository.VideoRepository
import com.example.videoplayer.fake.FakeDataSource
import com.example.videoplayer.fake.FakeMediaMetadataRetriever
import com.example.videoplayer.fake.FakeVideoApiService
import com.example.videoplayer.fake.FakeVideoInfoDao
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VideoRepositoryTest {

    private lateinit var repository: VideoRepository
    private val baseUrl = "http://source.com/"

    @Before
    fun createVideoRepository() {
        repository = VideoRepositoryImpl(
            videoApiService = FakeVideoApiService(),
            videoInfoMappers = VideoInfoMappers(baseUrl),
            videoInfoDao = FakeVideoInfoDao(),
            retrieverFactory = object : FFmpegMediaMetadataRetrieverFactory {
                override fun create(): FFmpegMediaMetadataRetrieverWrapper =
                    FakeMediaMetadataRetriever()
            },
        )
    }

    @Test
    fun testFetchVideoInfoFlow() = runTest {
        val fakeVideos = FakeDataSource.videosDto.categories[0].videos

        val videoList: List<VideoInfo> = repository.fetchVideoInfoFlow().toList()
        assertEquals(fakeVideos.size, videoList.size)

        val expectedVideoUrl1 = fakeVideos[0].sources[0]
        val expectedVideoUrl2 = fakeVideos[1].sources[0]
        assertEquals(expectedVideoUrl1, videoList[0].videoUrl)
        assertEquals(expectedVideoUrl2, videoList[1].videoUrl)

        assert(videoList[0].duration == FakeDataSource.videoDuration)
        assert(videoList[1].duration == FakeDataSource.videoDuration)
    }

    @Test
    fun testGetCachedVideoInfoList() = runBlocking {
        val cachedList: List<VideoInfo> = repository.getCachedVideoInfoList()
        assertEquals(2, cachedList.size)

        val expectedVideoUrl = FakeDataSource.videoInfoEntities[0].videoUrl
        assertEquals(expectedVideoUrl, cachedList[0].videoUrl)
    }
}
