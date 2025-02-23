package com.example.videoplayer

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.videoplayer.data.local.AppDatabase
import com.example.videoplayer.data.local.VideoInfoDao
import com.example.videoplayer.data.local.VideoInfoEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VideoInfoDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var videoInfoDao: VideoInfoDao

    private val videoInfo1 = VideoInfoEntity(
        videoUrl = "http://test1.com",
        title = "videoInfo1",
        thumbnailUrl = "http://test1.com/image",
        duration = "1:00"
    )
    private val videoInfo2 = VideoInfoEntity(
        videoUrl = "http://test2.com",
        title = "videoInfo2",
        thumbnailUrl = "http://test2.com/image",
        duration = "2:00"
    )
    private val videoInfo3 = VideoInfoEntity(
        videoUrl = "http://test3.com",
        title = "videoInfo3",
        thumbnailUrl = "http://test3.com/image",
        duration = "3:00"
    )
    private val initVideosList = listOf(videoInfo1, videoInfo2)
    private val finalVideosList = listOf(videoInfo1, videoInfo2, videoInfo3)

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        videoInfoDao = db.videoInfoDao()
    }

    private suspend fun initDb() {
        videoInfoDao.insertVideos(initVideosList)
    }

    @Test
    fun daoGetAllVideos_returnAllVideos() = runBlocking {
        initDb()
        val getVideosResult = videoInfoDao.getAllVideos()
        assertEquals(initVideosList, getVideosResult)
    }

    @Test
    fun daoInsertAndGetAllVideos_returnAllVideosListWithInserted() = runBlocking {
        initDb()
        val videosList = listOf(videoInfo3)
        videoInfoDao.insertVideos(videosList)
        val getVideosResult = videoInfoDao.getAllVideos()
        assertEquals(finalVideosList, getVideosResult)
    }

    @After
    fun closeDb() {
        db.close()
    }
}