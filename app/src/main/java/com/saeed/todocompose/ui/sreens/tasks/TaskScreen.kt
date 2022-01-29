package com.saeed.todocompose.ui.sreens.tasks

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.saeed.todocompose.components.PriorityDropDown
import com.saeed.todocompose.data.models.Priority
import com.saeed.todocompose.data.models.ToDoTask
import com.saeed.todocompose.ui.viewmodels.SharedViewModel
import com.saeed.todocompose.utils.Action

@Composable
fun TaskScreen(
    task: ToDoTask?,
    navigateToBack: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {

    val title:String by sharedViewModel.title
    val description:String by sharedViewModel.description
    val priority:Priority by sharedViewModel.priority
    val context = LocalContext.current


    Scaffold(topBar = { TaskAppBar(navigateToBack = {action->
        if (action == Action.NO_ACTION){
            navigateToBack(action)
        }else{
            if (sharedViewModel.validationField()){
                navigateToBack(action)
            }else{
                displayToast(context = context)
            }
        }

    }, task = task) }, content = {
        TaskContent(
            title = title,
            onTitleChange = {
                sharedViewModel.updateTitle(it)
            },
            description = description,
            onDescriptionChange = {
                sharedViewModel.description.value = it
            },
            priority = priority,
            onPriorityChange = {
                sharedViewModel.priority.value = it
            }
        )
    })
}

fun displayToast(context: Context) {
    Toast.makeText(context,"Field Empty",Toast.LENGTH_SHORT).show()
}
