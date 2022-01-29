package com.saeed.todocompose.data.models

import android.graphics.Color
import com.saeed.todocompose.ui.theme.HighColor
import com.saeed.todocompose.ui.theme.LowColor
import com.saeed.todocompose.ui.theme.MediumColor
import com.saeed.todocompose.ui.theme.NoneColor

enum class Priority(val color:androidx.compose.ui.graphics.Color){
    HIGH(HighColor),
    MEDIUM(MediumColor),
    LOW(LowColor),
    NONE(NoneColor)
}