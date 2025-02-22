package com.example.videoplayer.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoutes {

    @Serializable
    data object NavigateToList : NavRoutes

    @Serializable
    data class NavigateToPlayer(val url: String) : NavRoutes
}
