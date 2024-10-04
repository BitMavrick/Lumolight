package com.bitmavrick.lumolight.activity.core

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut

// * Optimized only for the forward transition

fun zoomInTransition(
    forwardTransitionSpeed : Int = 250
): EnterTransition {
    return scaleIn(
        initialScale = 0.85f,
        animationSpec = tween(
            durationMillis = forwardTransitionSpeed,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = forwardTransitionSpeed,
            easing = FastOutSlowInEasing
        )
    )
}

fun zoomOutTransition(
    forwardTransitionSpeed : Int = 250
): ExitTransition {
    return scaleOut(
        targetScale = 1.15f,
        animationSpec = tween(
            durationMillis = forwardTransitionSpeed,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = forwardTransitionSpeed,
            easing = FastOutSlowInEasing
        )
    )
}


// * Optimized only for the backward transition

fun popZoomInTransition(
    backwardTransitionSpeed: Int = 250
): EnterTransition {
    return scaleIn(
        initialScale = 1.15f,
        animationSpec = tween(
            durationMillis = backwardTransitionSpeed,
            easing  = LinearOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = backwardTransitionSpeed,
            easing = LinearOutSlowInEasing
        )
    )
}

fun popZoomOutTransition(
    backwardTransitionSpeed: Int = 250
): ExitTransition {
    return scaleOut(
        targetScale = 0.85f,
        animationSpec = tween(
            durationMillis = backwardTransitionSpeed,
            easing = LinearOutSlowInEasing
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = backwardTransitionSpeed,
            easing = LinearOutSlowInEasing
        )
    )
}