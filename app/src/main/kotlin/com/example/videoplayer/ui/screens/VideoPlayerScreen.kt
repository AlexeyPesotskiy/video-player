package com.example.videoplayer.ui.screens

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.annotation.OptIn
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import com.example.videoplayer.ui.VideoPlayerViewModel

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    videoUrl: String,
    onBack: () -> Unit,
    viewModel: VideoPlayerViewModel,
) {
    val context = LocalContext.current
    val orientation = LocalConfiguration.current.orientation

    LaunchedEffect(videoUrl) {
        if (viewModel.exoPlayer.currentMediaItem == null)
            viewModel.setMediaSource(videoUrl)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackButton(onBack)
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

@Composable
private fun BackButton(
    onBack: () -> Unit,
) {
    IconButton(
        onClick = onBack
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            ""
        )
    }
}