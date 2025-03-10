package com.example.videoplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.videoplayer.ui.core.slideInTransition
import com.example.videoplayer.ui.core.slideOutTransition
import com.example.videoplayer.ui.videoList.VideoListScreen
import com.example.videoplayer.ui.videoPlayer.VideoPlayerScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.NavigateToList,
    ) {
        composable<NavRoutes.NavigateToList>(
            enterTransition = { slideInTransition(-1.0) },
            exitTransition = { slideOutTransition(-1.0) }
        ) {
            VideoListScreen(
                onVideoListItemClick = { videoUrl: String ->
                    navController.navigate(
                        NavRoutes.NavigateToPlayer(videoUrl)
                    )
                },
            )
        }
        composable<NavRoutes.NavigateToPlayer>(
            enterTransition = { slideInTransition(1.0) },
            exitTransition = { slideOutTransition(1.0) }
        ) { navEntry ->
            val params = navEntry.toRoute<NavRoutes.NavigateToPlayer>()
            VideoPlayerScreen(
                videoUrl = params.url,
            )
        }
    }
}
