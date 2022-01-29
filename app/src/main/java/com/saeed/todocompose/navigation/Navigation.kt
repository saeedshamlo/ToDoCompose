package com.saeed.todocompose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saeed.todocompose.navigation.des.listComposable
import com.saeed.todocompose.navigation.des.splashComposable
import com.saeed.todocompose.navigation.des.taskComposable
import com.saeed.todocompose.ui.viewmodels.SharedViewModel
import com.saeed.todocompose.utils.Constants.LIST_SCREEN
import com.saeed.todocompose.utils.Constants.SPLASH_SCREEN

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val screens = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        splashComposable (
            navigateToListScreen = screens.splash
                )
        listComposable(
            navigateToTaskScreen = screens.list,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screens.task,
            sharedViewModel
        )
    }
}