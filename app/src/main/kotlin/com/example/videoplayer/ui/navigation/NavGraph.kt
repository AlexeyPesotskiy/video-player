package com.example.videoplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.videoplayer.ui.VideoListViewModel
import com.example.videoplayer.ui.screens.VideoListScreen
import com.example.videoplayer.ui.screens.VideoPlayerScreen
import com.example.videoplayer.ui.VideoPlayerViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.NavigateToList,
    ) {
        composable<NavRoutes.NavigateToList> {
            val videoListViewModel: VideoListViewModel = hiltViewModel()
//            TODO
            VideoListScreen(
                viewModel = videoListViewModel,
                onVideoListItemClick = { videoUrl: String ->
                    navController.navigate(
                        NavRoutes.NavigateToPlayer(videoUrl)
                    )
                }
            )
        }

        composable<NavRoutes.NavigateToPlayer> { navEntry ->
            val params = navEntry.toRoute<NavRoutes.NavigateToPlayer>()
            val viewModel: VideoPlayerViewModel = hiltViewModel()

            VideoPlayerScreen(
                videoUrl = params.url,
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack(
                        NavRoutes.NavigateToList,
                        false
                    )
                }
            )
        }
    }
}