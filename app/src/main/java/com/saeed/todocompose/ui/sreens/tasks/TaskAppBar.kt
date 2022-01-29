package com.saeed.todocompose.ui.sreens.tasks

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.saeed.todocompose.R
import com.saeed.todocompose.components.DisplayAlertDialog
import com.saeed.todocompose.data.models.Priority
import com.saeed.todocompose.data.models.ToDoTask
import com.saeed.todocompose.ui.theme.backgroundAppBarColor
import com.saeed.todocompose.ui.theme.topAppBarColor
import com.saeed.todocompose.utils.Action
import org.w3c.dom.Text

@Composable
fun TaskAppBar(navigateToBack: (Action) -> Unit, task: ToDoTask?) {


    if (task == null) {
        NewTaskAppBar(navigateToBack = navigateToBack)
    } else {
        ExitingTaskAppBar(selectedTask = task, navigateToBack = navigateToBack)
    }


}

@Composable
fun NewTaskAppBar(navigateToBack: (Action) -> Unit) {
    TopAppBar(navigationIcon = {
        BackAction(onBackClicked = navigateToBack)

    }, title = {
        Text(
            text = "Add Task",
            color = MaterialTheme.colors.topAppBarColor
        )
    },
        backgroundColor = MaterialTheme.colors.backgroundAppBarColor,
        actions = {
            AddAction(onAddClicked = navigateToBack)
        }
    )
}


@Composable
fun BackAction(onBackClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onBackClicked(Action.NO_ACTION)

    }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colors.topAppBarColor
        )

    }
}

@Composable
fun AddAction(onAddClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onAddClicked(Action.ADD)

    }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
            tint = MaterialTheme.colors.topAppBarColor
        )

    }
}


@Composable
fun ExitingTaskAppBar(
    selectedTask: ToDoTask, navigateToBack: (Action) -> Unit
) {
    TopAppBar(navigationIcon = {
        CloseAction(onCloseClicked = navigateToBack)

    }, title = {
        Text(
            text = selectedTask.title,
            color = MaterialTheme.colors.topAppBarColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    },
        backgroundColor = MaterialTheme.colors.backgroundAppBarColor,
        actions = {
            ExitingTaskAppBarAction(selectedTask = selectedTask,navigateToBack = navigateToBack)
        }
    )
}

@Composable
fun ExitingTaskAppBarAction(
    selectedTask: ToDoTask,
    navigateToBack: (Action) -> Unit
) {


    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_task, selectedTask.title),
        message = stringResource(id = R.string.delete_task_confim, selectedTask.title),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
    onYesClicked = {
        navigateToBack(Action.DELETE)
    })

    DeleteAction(onDeleteClicked = {
        openDialog = true
    })
    UpdateAction(onUpdateClicked = navigateToBack)
}

@Composable
fun CloseAction(onCloseClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onCloseClicked(Action.NO_ACTION)

    }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
            tint = MaterialTheme.colors.topAppBarColor
        )

    }
}

@Composable
fun DeleteAction(onDeleteClicked: () -> Unit) {
    IconButton(onClick = {
        onDeleteClicked()

    }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete",
            tint = MaterialTheme.colors.topAppBarColor
        )

    }
}

@Composable
fun UpdateAction(onUpdateClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onUpdateClicked(Action.UPDATE)

    }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Update",
            tint = MaterialTheme.colors.topAppBarColor
        )

    }
}

@Preview
@Composable
fun NewTaskAppBarPre() {
    NewTaskAppBar(navigateToBack = {})

}

@Preview
@Composable
fun ExitTaskAppBarPre() {
    ExitingTaskAppBar(
        navigateToBack = {}, selectedTask = ToDoTask(
            id = 1,
            title = "Saeed",
            description = "random",
            priority = Priority.HIGH
        )
    )

}