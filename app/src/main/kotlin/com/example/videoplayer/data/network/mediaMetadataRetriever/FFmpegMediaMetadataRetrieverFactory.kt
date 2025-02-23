package com.example.videoplayer.data.network.mediaMetadataRetriever

interface FFmpegMediaMetadataRetrieverFactory {
    fun create(): FFmpegMediaMetadataRetrieverWrapper
}
