package com.example.videoplayer.ui.core

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.IntOffset

fun slideOutTransition(directionMultiplier: Double) = slideOutHorizontally(
    tweenAnimationSpec,
    targetOffsetX = { (it * directionMultiplier).toInt() }
)

fun slideInTransition(directionMultiplier: Double) = slideInHorizontally(
    tweenAnimationSpec,
    initialOffsetX = { (it * directionMultiplier).toInt() }
)

val tweenAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = 400)
