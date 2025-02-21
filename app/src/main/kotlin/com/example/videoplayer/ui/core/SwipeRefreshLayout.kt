package com.example.videoplayer.ui.core

import android.content.Context
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ComposeSwipeRefreshLayout(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AndroidView(
        factory = { context: Context ->
            androidx.swiperefreshlayout.widget.SwipeRefreshLayout(context).apply {
                isNestedScrollingEnabled = false

                setOnChildScrollUpCallback { _, _ ->
                    lazyListState.firstVisibleItemIndex != 0 ||
                            lazyListState.firstVisibleItemScrollOffset > 0
                }

                setOnRefreshListener { onRefresh() }

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
        modifier = modifier.statusBarsPadding()
    )
}