package com.saeed.todocompose.ui.sreens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saeed.todocompose.data.models.Priority
import com.saeed.todocompose.data.models.ToDoTask
import com.saeed.todocompose.navigation.Screens
import com.saeed.todocompose.ui.theme.HighColor
import com.saeed.todocompose.ui.theme.taskItemBackgroundColor
import com.saeed.todocompose.ui.theme.taskItemTextColor
import com.saeed.todocompose.utils.Action
import com.saeed.todocompose.utils.RequestState
import com.saeed.todocompose.utils.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    tasks: RequestState<List<ToDoTask>>,
    searchTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    lowPriorityTask: List<ToDoTask>,
    highPriorityTask: List<ToDoTask>,
    sortState: RequestState<Priority>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreens: (taskId: Int) -> Unit
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreens = navigateToTaskScreens
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (tasks is RequestState.Success) {
                    HandleListContent(
                        tasks = tasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreens = navigateToTaskScreens
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTask,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreens = navigateToTaskScreens
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTask,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreens = navigateToTaskScreens
                )
            }
        }


    }


}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreens: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreens = navigateToTaskScreens
        )
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreens: (taskId: Int) -> Unit
) {

    LazyColumn {
        items(
            items = tasks,
            key = { task ->
                task.id
            }) { task ->


            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope  = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDelete(Action.DELETE, task)
                }

            }
            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -90f
            )

            var itemAppared by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true) {
                itemAppared = true
            }
            AnimatedVisibility(
                visible = itemAppared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.2f) },
                    background = {
                        RedBackground(degrees = degrees)
                    }, dismissContent = {
                        TaskItem(toDoTask = task, navigateToTaskScreens = navigateToTaskScreens)
                    })
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighColor)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreens: (taskId: Int) -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = 2.dp,
        onClick = {
            navigateToTaskScreens(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 12.dp)
                .fillMaxWidth(),

            ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(16.dp)
                    ) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                text = toDoTask.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun TaskItemPreView() {
    TaskItem(
        toDoTask = ToDoTask(0, "Title", "some random text", Priority.HIGH),
        navigateToTaskScreens = {})
}