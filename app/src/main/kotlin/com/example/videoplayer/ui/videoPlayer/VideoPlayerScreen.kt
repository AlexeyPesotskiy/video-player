package com.example.videoplayer.ui.videoPlayer

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING

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

    AndroidView(
        factory = {
            playerViewInit(
                context = context,
                orientation = orientation,
                viewModel = viewModel
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(Color.Black)
    )

    ExoPlayerPauseObserver(
        onPause = { viewModel.pauseExoPlayer() }
    )
}

@OptIn(UnstableApi::class)
private fun playerViewInit(
    context: Context,
    orientation: Int,
    viewModel: VideoPlayerViewModel,
) = PlayerView(context).apply {
        player = viewModel.exoPlayer

        setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
        setShowNextButton(false)
        setShowPreviousButton(false)
        if (orientation == ORIENTATION_LANDSCAPE) {
            setFullscreenButtonClickListener {
                resizeMode = if (it)
                    RESIZE_MODE_ZOOM
                else
                    RESIZE_MODE_FIT
            }
        }
    }

@Composable
private fun ExoPlayerPauseObserver(onPause: () -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                onPause()
            }
        }
        lifecycle.addObserver(
            observer
        )
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}
