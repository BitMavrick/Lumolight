package com.bitmavrick.theme

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

fun darkenColor(color: Color, factor: Float): Color {
    return lerp(color, Color.Black, factor)
}

@Composable
fun LumolightTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    amoledMode: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    var colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val contentTargetFactor = remember(amoledMode) { if (amoledMode) 0.1f else 0f }
    val contentDimmingFactor by animateFloatAsState(
        targetValue = contentTargetFactor,
        animationSpec = tween(durationMillis = 1000) // 1 second duration
    )
    val backgroundTargetFactor = remember(amoledMode) { if (amoledMode) 1f else 0f }
    val backgroundDimmingFactor by animateFloatAsState(
        targetValue = backgroundTargetFactor,
        animationSpec = tween(durationMillis = 1000) // 1 second duration
    )

    if (darkTheme) colorScheme = colorScheme.copy(
        background = darkenColor(colorScheme.background, backgroundDimmingFactor),
        surface = darkenColor(colorScheme.surface, backgroundDimmingFactor),
        surfaceContainer = darkenColor(colorScheme.surfaceContainer, backgroundDimmingFactor),
        surfaceVariant = darkenColor(colorScheme.surfaceVariant, contentDimmingFactor),
        surfaceContainerLowest = darkenColor(
            colorScheme.surfaceContainerLowest, contentDimmingFactor
        ),
        surfaceContainerLow = darkenColor(
            colorScheme.surfaceContainerLow, contentDimmingFactor
        ),
        surfaceContainerHigh = darkenColor(
            colorScheme.surfaceContainerHigh, contentDimmingFactor
        ),
        surfaceContainerHighest = darkenColor(
            colorScheme.surfaceContainerHighest, contentDimmingFactor
        ),
        surfaceDim = darkenColor(colorScheme.surfaceDim, contentDimmingFactor),
        surfaceBright = darkenColor(colorScheme.surfaceBright, contentDimmingFactor),
        scrim = darkenColor(colorScheme.scrim, contentDimmingFactor),
        inverseSurface = darkenColor(colorScheme.inverseSurface, contentDimmingFactor),
        errorContainer = darkenColor(colorScheme.errorContainer, contentDimmingFactor),
        tertiaryContainer = darkenColor(colorScheme.tertiaryContainer, contentDimmingFactor),
        secondaryContainer = darkenColor(colorScheme.secondaryContainer, contentDimmingFactor),
        primaryContainer = darkenColor(colorScheme.primaryContainer, contentDimmingFactor)
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}