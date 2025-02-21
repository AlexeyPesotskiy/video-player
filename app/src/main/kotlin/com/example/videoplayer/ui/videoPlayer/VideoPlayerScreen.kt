package com.example.videoplayer.ui.videoPlayer

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    videoUrl: String,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val orientation = LocalConfiguration.current.orientation

    LaunchedEffect(videoUrl) {
        if (viewModel.exoPlayer.currentMediaItem == null)
            viewModel.setMediaSource(videoUrl)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = viewModel.exoPlayer
                    setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                    if (orientation == ORIENTATION_LANDSCAPE) {
                        setFullscreenButtonClickListener {
                            resizeMode = if (it)
                                RESIZE_MODE_FILL
                            else
                                RESIZE_MODE_FIT
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
