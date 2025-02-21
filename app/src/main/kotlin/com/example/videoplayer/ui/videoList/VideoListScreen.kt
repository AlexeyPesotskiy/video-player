package com.example.videoplayer.ui.videoList

import androidx.compose.foundation.lazy.rememberLazyListState
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
        viewModel.getVideoInfoList()
    }

    VideoListScreenContent(
        state = state,
        onUpdateVideoList = onUpdateVideoList,
        onVideoListItemClick = onVideoListItemClick,
    )
}

@Composable
private fun VideoListScreenContent(
    state: VideoListUiState,
    onUpdateVideoList: () -> Unit,
    onVideoListItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    ComposeSwipeRefreshLayout(
        isRefreshing = state.isLoading,
        onRefresh = onUpdateVideoList,
        lazyListState = listState,
    ) {
        if (state.isError)
            ErrorScreen(modifier)
        else
            VideoList(
                state = state,
                onVideoListItemClick = onVideoListItemClick,
                lazyListState = listState,
                modifier = modifier
            )
    }
}
