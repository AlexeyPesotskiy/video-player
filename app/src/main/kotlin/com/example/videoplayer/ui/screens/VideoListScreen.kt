package com.example.videoplayer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.ui.VideoListUiState
import com.example.videoplayer.ui.theme.VideoPlayerTheme

@Composable
fun VideoListScreen(
    state: VideoListUiState.Success,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.videoInfoList) {
            VideoInfoCard(it)
        }
    }
}

@Composable
private fun VideoInfoCard(
    videoInfo: VideoInfo
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(videoInfo.thumbnailUrl)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                videoInfo.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                "Видео ID: ${videoInfo.videoId}"
            )
            Text(
                "Длительность: ${videoInfo.duration}"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VideoInfoCardPreview() {
    VideoPlayerTheme {
        VideoInfoCard(
            VideoInfo(
                videoId = "video_id",
                title = "Top video",
                thumbnailUrl = "https://i.ytimg.com/vi/6ORBFXYlQ3U/maxresdefault.jpg",
                duration = "05:45:25",
            )
        )
    }
}