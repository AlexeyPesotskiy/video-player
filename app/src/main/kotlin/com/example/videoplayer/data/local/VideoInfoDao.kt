package com.example.videoplayer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoInfoDao {
    @Query("SELECT * FROM videos_info")
    suspend fun getAllVideos(): List<VideoInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoInfoEntity>)

    @Query("DELETE FROM videos_info WHERE videoUrl NOT IN (:urls)")
    suspend fun deleteOldVideos(urls: List<String>)
}
