package com.example.videoplayer.fake

import com.example.videoplayer.data.local.VideoInfoDao
import com.example.videoplayer.data.local.VideoInfoEntity

class FakeVideoInfoDao: VideoInfoDao {
    override suspend fun getAllVideos(): List<VideoInfoEntity> =
        FakeDataSource.videoInfoEntities

    override suspend fun insertVideos(videos: List<VideoInfoEntity>) {}

    override suspend fun deleteOldVideos(urls: List<String>) {}
}