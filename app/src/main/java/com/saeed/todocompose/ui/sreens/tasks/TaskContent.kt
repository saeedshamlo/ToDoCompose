package com.saeed.todocompose.ui.sreens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saeed.todocompose.components.PriorityDropDown
import com.saeed.todocompose.data.models.Priority

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPriorityChange: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(all = 12.dp)

    ) {
        OutlinedTextField(
            value = title, onValueChange = { onTitleChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Title") },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            shape = RoundedCornerShape(12.dp)

        )
        Divider(modifier = Modifier.height(8.dp),color = MaterialTheme.colors.background)
        PriorityDropDown(

            priority = priority,
            onPrioritySelected = onPriorityChange)
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            label = { Text(text = "Description")},
            textStyle = MaterialTheme.typography.body1,
            value = description, onValueChange = { onDescriptionChange(it) },
        shape = RoundedCornerShape(12.dp))

    }

}

@Preview(showBackground = true)
@Composable
fun TaskContentPre() {
    TaskContent(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.HIGH,
        onPriorityChange = {}
    )
}

