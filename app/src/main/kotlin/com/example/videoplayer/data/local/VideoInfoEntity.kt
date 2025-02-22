package com.example.videoplayer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos_info")
data class VideoInfoEntity(
    @PrimaryKey
    val videoUrl: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: String,
)
