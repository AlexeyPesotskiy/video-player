package com.example.videoplayer.data.di

import com.example.videoplayer.data.network.mediaMetadataRetriever.FFmpegMediaMetadataRetrieverFactory
import com.example.videoplayer.data.network.mediaMetadataRetriever.FFmpegMediaMetadataRetrieverFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaMetadataRetrieverModule {

    @Binds
    abstract fun provideFFmpegMediaMetadataRetrieverFactory(
        mediaMetadataRetrieverFactoryImpl: FFmpegMediaMetadataRetrieverFactoryImpl,
    ): FFmpegMediaMetadataRetrieverFactory
}
