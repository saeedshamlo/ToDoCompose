package com.saeed.todocompose.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saeed.todocompose.data.models.Priority
import com.saeed.todocompose.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority){
    Row(verticalAlignment = Alignment.CenterVertically) {
       Canvas(modifier = Modifier.size(16.dp)){
           drawCircle(color = priority.color)
       }
        Text(modifier = Modifier.padding(start = 6.dp),
            text = priority.name ,
            style = Typography.subtitle2 ,
            color = MaterialTheme.colors.onSurface)

    }
}

@Composable
@Preview
fun PriorityItemPreview(){
    PriorityItem(priority = Priority.LOW)
}