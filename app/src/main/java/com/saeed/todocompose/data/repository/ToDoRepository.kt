package com.saeed.todocompose.data.repository

import com.saeed.todocompose.data.ToDoDao
import com.saeed.todocompose.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao){

    val getAllTask:Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLow :Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHigh :Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId:Int):Flow<ToDoTask>{
        return toDoDao.getSelectedTask(taskId)
    }

    suspend fun addTask(toDoTask: ToDoTask){
        toDoDao.addTask(toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask){
        toDoDao.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask){
        toDoDao.deleteTask(toDoTask)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAllTask()
    }

    fun search(query:String):Flow<List<ToDoTask>>{
        return toDoDao.search(query = query)
    }
}