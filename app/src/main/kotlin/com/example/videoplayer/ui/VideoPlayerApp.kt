package com.example.videoplayer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.videoplayer.ui.screens.ErrorScreen
import com.example.videoplayer.ui.screens.LoadingScreen
import com.example.videoplayer.ui.screens.VideoListScreen

@Composable
fun VideoPlayerApp() {
    val videoListViewModel: VideoListViewModel = hiltViewModel()
    val state = videoListViewModel.uiState.collectAsState().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        when (state) {
            is VideoListUiState.Success ->
                VideoListScreen(state, modifier)

            VideoListUiState.Error -> {
                ErrorScreen(modifier)
            }

            VideoListUiState.Loading -> {
                LoadingScreen(modifier)
            }
        }
    }
}