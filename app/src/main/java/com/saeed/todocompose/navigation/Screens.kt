package com.saeed.todocompose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.saeed.todocompose.utils.Action
import com.saeed.todocompose.utils.Constants.LIST_SCREEN
import com.saeed.todocompose.utils.Constants.SPLASH_SCREEN

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate("list/${Action.NO_ACTION}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    val list: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }

    val task: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }


}