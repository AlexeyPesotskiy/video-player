package com.example.videoplayer.data.di

import com.example.videoplayer.data.repository.VideoRepositoryImpl
import com.example.videoplayer.domain.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideVideoRepository(
        videoRepositoryImpl: VideoRepositoryImpl
    ): VideoRepository
}
