package com.saeed.todocompose.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF217CF3)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowColor = Color(0xFF00C980)
val MediumColor = Color(0XFFFFC114)
val HighColor = Color(0xFFFF4646)
val NoneColor = MediumGray

val Colors.splashScreenBack: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Purple700

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.topAppBarColor: Color
    @Composable
    get() = if (isLight) Color.White else Color.LightGray

val Colors.backgroundAppBarColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black