package com.example.videoplayer.ui.videoList

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.videoplayer.R
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.ui.core.ErrorDialog
import com.example.videoplayer.ui.theme.VideoPlayerTheme

@Composable
fun VideoList(
    state: VideoListUiState,
    onVideoListItemClick: (String) -> Unit,
    onErrorDialogDismiss: () -> Unit,
    lazyListState: LazyGridState,
    modifier: Modifier = Modifier,
) {
    val isOrientationLandscape =
        LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE

    LazyVerticalGrid(
        modifier = modifier.run {
            if (isOrientationLandscape)
                padding(horizontal = 40.dp)
            else
                this
        },
        columns = if (isOrientationLandscape)
            GridCells.Adaptive(220.dp)
        else
            GridCells.Fixed(1),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(state.videoInfoList) {
            VideoInfoCard(
                videoInfo = it,
                onVideoInfoCardClick = onVideoListItemClick,
            )
        }
    }
    if (state.isError)
        ErrorDialog(
            errorMessage = stringResource(R.string.video_list_screen_error_message),
            onDismiss = onErrorDialogDismiss)
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
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
            )
        }
    }
}

@Composable
private fun VideoThumbnail(
    thumbnailUrl: String,
    duration: String,
) {
    Box {
        val placeholderModifier = Modifier.padding(16.dp).height(64.dp).align(Alignment.Center)
        VideoThumbnailImage(thumbnailUrl, placeholderModifier)

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

@Composable
fun VideoThumbnailImage(
    thumbnailUrl: String,
    placeholderModifier: Modifier,
) {
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
                duration = "5:45:25",
            ),
            onVideoInfoCardClick = {}
        )
    }
}
