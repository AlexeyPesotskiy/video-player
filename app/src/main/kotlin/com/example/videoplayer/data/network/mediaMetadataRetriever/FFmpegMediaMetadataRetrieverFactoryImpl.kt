package com.example.videoplayer.data.network.mediaMetadataRetriever

import wseemann.media.FFmpegMediaMetadataRetriever
import javax.inject.Inject

class FFmpegMediaMetadataRetrieverFactoryImpl @Inject constructor() :
    FFmpegMediaMetadataRetrieverFactory {
    override fun create(): FFmpegMediaMetadataRetrieverWrapper =
        object : FFmpegMediaMetadataRetriever(), FFmpegMediaMetadataRetrieverWrapper {}
}
