package com.example.videoplayer.ui.videoList

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.videoplayer.ui.core.ComposeSwipeRefreshLayout

@Composable
fun VideoListScreen(
    onVideoListItemClick: (String) -> Unit,
    viewModel: VideoListViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState().value
    val onUpdateVideoList: () -> Unit = {
        viewModel.fetchVideoInfoList()
    }
    val onErrorDialogDismiss: () -> Unit = {
        viewModel.clearError()
    }

    VideoListScreenContent(
        state = state,
        onUpdateVideoList = onUpdateVideoList,
        onErrorDialogDismiss = onErrorDialogDismiss,
        onVideoListItemClick = onVideoListItemClick,
    )
}

@Composable
private fun VideoListScreenContent(
    state: VideoListUiState,
    onUpdateVideoList: () -> Unit,
    onErrorDialogDismiss: () -> Unit,
    onVideoListItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()

    ComposeSwipeRefreshLayout(
        isRefreshing = state.isLoading,
        onRefresh = onUpdateVideoList,
        lazyGridState = gridState,
    ) {
        VideoList(
            state = state,
            onVideoListItemClick = onVideoListItemClick,
            onErrorDialogDismiss = onErrorDialogDismiss,
            lazyListState = gridState,
            modifier = modifier
        )
    }
}
