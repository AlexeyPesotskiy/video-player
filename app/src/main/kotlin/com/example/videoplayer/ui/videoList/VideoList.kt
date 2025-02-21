package com.example.videoplayer.ui.videoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.videoplayer.R
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.ui.theme.VideoPlayerTheme

@Composable
fun VideoList(
    state: VideoListUiState,
    onVideoListItemClick: (String) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.videoInfoList) {
            VideoInfoCard(
                videoInfo = it,
                onVideoInfoCardClick = onVideoListItemClick,
            )
        }
    }
}

@Composable
private fun VideoInfoCard(
    videoInfo: VideoInfo,
    onVideoInfoCardClick: (String) -> Unit,
) {
    Card(
        onClick = { onVideoInfoCardClick(videoInfo.videoUrl) },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            VideoThumbnail(
                thumbnailUrl = videoInfo.thumbnailUrl,
                duration = videoInfo.duration
            )
            Text(
                videoInfo.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun VideoThumbnail(
    thumbnailUrl: String,
    duration: String,
) {
    val placeholderModifier = Modifier.padding(32.dp)
    Box {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailUrl)
                .build(),
            contentDescription = "",
            error = {
                Icon(
                    Icons.Default.Close,
                    stringResource(R.string.error),
                    placeholderModifier
                )
            },
            loading = {
                CircularProgressIndicator(modifier = placeholderModifier)
            },
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            duration,
            color = Color.White,
            textAlign = TextAlign.Right,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .clip(RoundedCornerShape(20))
                .background(
                    Color.Black.copy(alpha = 0.75F)
                )
                .padding(horizontal = 2.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VideoInfoCardPreview() {
    VideoPlayerTheme {
        VideoInfoCard(
            VideoInfo(
                videoUrl = "video_id",
                title = "Top video",
                thumbnailUrl = "https://i.ytimg.com/vi/6ORBFXYlQ3U/maxresdefault.jpg",
                duration = "05:45:25",
            ),
            onVideoInfoCardClick = {}
        )
    }
}