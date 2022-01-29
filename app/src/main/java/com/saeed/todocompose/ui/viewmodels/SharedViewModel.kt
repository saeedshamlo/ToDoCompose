package com.saeed.todocompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.todocompose.data.models.Priority
import com.saeed.todocompose.data.models.ToDoTask
import com.saeed.todocompose.data.repository.DataStoreRepository
import com.saeed.todocompose.data.repository.ToDoRepository
import com.saeed.todocompose.utils.Action
import com.saeed.todocompose.utils.RequestState
import com.saeed.todocompose.utils.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataSoRepository: DataStoreRepository
) : ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSE)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idl)
    val allTask: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _searchTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idl)
    val searchTask: StateFlow<RequestState<List<ToDoTask>>> = _searchTask


    fun persistSortingState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSoRepository.persistSortState(priority = priority)
        }
    }

    val lowPriorityTask :StateFlow<List<ToDoTask>> =
        repository.sortByLow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )
    val highPriorityTask :StateFlow<List<ToDoTask>> =
        repository.sortByHigh.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )
    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idl)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataSoRepository.readSortState.map {
                    Priority.valueOf(it)
                }.collect {
                    _sortState.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }

    }

    fun getSearchTask(searchQuery: String) {
        _searchTask.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.search("%$searchQuery%").collect { searchTasks ->
                    _searchTask.value = RequestState.Success(searchTasks)
                }
            }

        } catch (e: Exception) {
            _searchTask.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED

    }

    fun getAllTask() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTask.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }

    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect { task ->
                _selectedTask.value = task

            }
        }
    }

    fun updateTaskFields(task: ToDoTask?) {
        if (task != null) {
            id.value = task.id
            title.value = task.title
            description.value = task.description
            priority.value = task.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )

            repository.addTask(toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSE
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(toDoTask)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }


    fun handleDatabaseAction(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTask()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < 20) {
            title.value = newTitle
        } else {

        }

    }


    fun validationField(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }

}