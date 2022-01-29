package com.saeed.todocompose.ui.sreens.list

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.saeed.todocompose.ui.theme.fabBackgroundColor
import com.saeed.todocompose.ui.viewmodels.SharedViewModel
import com.saeed.todocompose.utils.Action
import com.saeed.todocompose.utils.SearchAppBarState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTask()
        sharedViewModel.readSortState()
    }
    val action by sharedViewModel.action
    val allTasks by sharedViewModel.allTask.collectAsState()
    val searchTasks by sharedViewModel.searchTask.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTask by sharedViewModel.lowPriorityTask.collectAsState()
    val highPriorityTask by sharedViewModel.highPriorityTask.collectAsState()

    val scaffoldState = rememberScaffoldState()
    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseAction(action) },
        taskTitle = sharedViewModel.title.value,
        action = action,
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        duration = if (action == Action.DELETE) SnackbarDuration.Long else SnackbarDuration.Short
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                tasks = allTasks,
                navigateToTaskScreens = navigateToTaskScreen,
                searchTasks = searchTasks,
                searchAppBarState = searchAppBarState,
                lowPriorityTask = lowPriorityTask,
                highPriorityTask = highPriorityTask,
                sortState = sortState,
                onSwipeToDelete = {action, toDoTask ->  
                    sharedViewModel.action.value =action
                    sharedViewModel.updateTaskFields(task = toDoTask)
                }
            )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen)
        }
    )
}
@Composable
fun ListFab(
    onFabClick: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClick(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor

    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onUndoClicked: (Action) -> Unit,
    handleDatabaseActions: () -> Unit,
    taskTitle: String,
    action: Action,
    duration: SnackbarDuration
) {
    handleDatabaseActions()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action,taskTitle),
                    actionLabel = setActionLabel(action), duration = duration
                )
                undoDeleteTask(
                    action, snackBarResult, onUndoClicked =
                    onUndoClicked
                )
            }
        }
    }
}

private fun setMessage(action: Action,taskTitle: String):String{
    return when(action){
        Action.DELETE_ALL ->"All Tasks Removed"
        else ->  "${action.name}: $taskTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeleteTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }

}
