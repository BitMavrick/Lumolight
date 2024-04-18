package com.bitmavrick.lumolight.activity.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.ui.screen.home.HomeScreen
import com.bitmavrick.lumolight.ui.screen.setting.SettingScreen

@Composable
fun Lumolight() {
    val navController = rememberNavController()
    val enterAnimationDuration = 500
    val exitAnimationDuration = 450
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        enterTransition = {
            fadeIn(animationSpec = tween(enterAnimationDuration)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up, tween(enterAnimationDuration)
            )
        },
        exitTransition =  {
            fadeOut(animationSpec = tween(exitAnimationDuration)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Up, tween(exitAnimationDuration)
            )
        },

        popEnterTransition = {
            fadeIn(animationSpec = tween(enterAnimationDuration)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Down, tween(enterAnimationDuration)
            )
        },

        popExitTransition = {
            fadeOut(animationSpec = tween(exitAnimationDuration)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down, tween(exitAnimationDuration)
            )
        }

    ) {
        composable(route = Screen.HomeScreen.route){
            HomeScreen(
                navController = navController
            )
        }
        composable(route = Screen.SettingScreen.route){
            SettingScreen(navController = navController)
        }
    }
}

sealed class Screen(val route: String){
    data object HomeScreen : Screen("home_screen")
    data object SettingScreen : Screen("setting_screen")
}