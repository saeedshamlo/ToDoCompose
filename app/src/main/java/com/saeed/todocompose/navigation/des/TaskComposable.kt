package com.saeed.todocompose.navigation.des

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
//import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.saeed.todocompose.ui.sreens.tasks.TaskScreen
import com.saeed.todocompose.ui.viewmodels.SharedViewModel
import com.saeed.todocompose.utils.Action
import com.saeed.todocompose.utils.Constants
import com.saeed.todocompose.utils.Constants.TASK_ARGUMENT_KEY
import com.saeed.todocompose.utils.Constants.TASK_SCREEN

@ExperimentalAnimationApi
fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        }),


    ) { navBackStackEntry ->

        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        LaunchedEffect(key1 = taskId){
            sharedViewModel.getSelectedTask(taskId)
        }
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask){
            if (selectedTask !=null || taskId == -1){
                sharedViewModel.updateTaskFields(task = selectedTask)
            }

        }
        TaskScreen(navigateToBack = navigateToListScreen, task = selectedTask,sharedViewModel = sharedViewModel)

    }
}