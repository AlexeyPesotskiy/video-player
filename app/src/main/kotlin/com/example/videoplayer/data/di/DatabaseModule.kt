package com.example.videoplayer.data.di

import android.content.Context
import androidx.room.Room
import com.example.videoplayer.data.local.AppDatabase
import com.example.videoplayer.data.local.VideoInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): AppDatabase = Room
        .databaseBuilder(
            context,
            AppDatabase::class.java,
            "videos_db"
        )
        .build()

    @Provides
    fun provideVideoDao(database: AppDatabase): VideoInfoDao {
        return database.videoDao()
    }
}
