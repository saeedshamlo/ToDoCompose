package com.saeed.todocompose.navigation.des

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saeed.todocompose.ui.splash.SplashScreen
import com.saeed.todocompose.ui.sreens.list.ListScreen
import com.saeed.todocompose.ui.viewmodels.SharedViewModel
import com.saeed.todocompose.utils.Constants
import com.saeed.todocompose.utils.Constants.SPLASH_SCREEN
import com.saeed.todocompose.utils.toAction


@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN
    ) {
        SplashScreen(navigateToListScreen)

    }
}