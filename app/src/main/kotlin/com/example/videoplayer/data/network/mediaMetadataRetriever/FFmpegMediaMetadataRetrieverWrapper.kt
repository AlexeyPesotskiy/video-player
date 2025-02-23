package com.example.videoplayer.data.network.mediaMetadataRetriever

interface FFmpegMediaMetadataRetrieverWrapper {
    fun extractMetadata(key: String): String?

    fun setDataSource(path: String)

    fun release()
}
