package com.example.videoplayer.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.videoplayer.ui.VideoListViewModel

@Composable
fun VideoListScreen(
    viewModel: VideoListViewModel,
    onVideoListItemClick: (String) -> Unit,
) {
    val state = viewModel.uiState.collectAsState().value
    val modifier = Modifier.fillMaxSize()
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        val listState = rememberLazyListState()

        NativeSwipeRefreshLayout(
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.getVideoInfoList() },
            lazyListState = listState,
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (state.isError) {
                ErrorScreen(modifier)
            } else {
                VideoList(
                    state,
                    onVideoListItemClick,
                    listState,
                    modifier
                )
            }
        }
    }
}

@Composable
fun NativeSwipeRefreshLayout(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AndroidView(
        factory = { context: Context ->
            SwipeRefreshLayout(context).apply {
                isNestedScrollingEnabled = false

                setOnChildScrollUpCallback { _, _ ->
                    lazyListState.firstVisibleItemIndex != 0 ||
                            lazyListState.firstVisibleItemScrollOffset > 0
                }

                setOnRefreshListener {
                    onRefresh()
                }
                val composeView = ComposeView(context).apply {
                    isNestedScrollingEnabled = false
                    setContent { content() }
                }
                addView(composeView)
            }
        },
        update = { swipeLayout ->
            swipeLayout.isRefreshing = isRefreshing
        },
        modifier = modifier
    )
}
