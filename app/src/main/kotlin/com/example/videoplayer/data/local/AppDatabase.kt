package com.example.videoplayer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [VideoInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoInfoDao(): VideoInfoDao
}
