package com.example.videoplayer.fake

import com.example.videoplayer.data.network.mediaMetadataRetriever.FFmpegMediaMetadataRetrieverWrapper
import wseemann.media.FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION

class FakeMediaMetadataRetriever : FFmpegMediaMetadataRetrieverWrapper {

    override fun extractMetadata(key: String): String? =
        if (key == METADATA_KEY_DURATION)
            FakeDataSource.videoDurationInMs
        else
            null

    override fun setDataSource(path: String) {}

    override fun release() {}
}