package com.example.videoplayer.ui.core

import android.content.Context
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ComposeSwipeRefreshLayout(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AndroidView(
        factory = { context: Context ->
            androidx.swiperefreshlayout.widget.SwipeRefreshLayout(context).apply {
                isNestedScrollingEnabled = false

                setOnChildScrollUpCallback { _, _ ->
                    lazyGridState.firstVisibleItemIndex != 0 ||
                            lazyGridState.firstVisibleItemScrollOffset > 0
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
        modifier = modifier.systemBarsPadding()
    )
}
