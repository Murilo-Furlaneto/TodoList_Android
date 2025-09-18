package br.com.furlaneto.murilo.todolist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.todolist.data.repository.TaskRepository
import br.com.furlaneto.murilo.todolist.model.Task
import br.com.furlaneto.murilo.todolist.services.validation.Field
import br.com.furlaneto.murilo.todolist.services.validation.ValidationResult
import br.com.furlaneto.murilo.todolist.services.validation.ValidationTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val validationTask: ValidationTask
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()

    private val _titleError = MutableStateFlow<String?>(null)
    val titleError: StateFlow<String?> = _titleError.asStateFlow()

    private val _descriptionError = MutableStateFlow<String?>(null)
    val descriptionError: StateFlow<String?> = _descriptionError.asStateFlow()

    val completedTasks: StateFlow<List<Task>> = taskList
        .map { list -> list.filter { it.isCompleted } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = taskRepository.getAllTasks()
        }
    }

    fun addTask(task: Task): Boolean {
        when (val validation = validationTask.validate(task)) {
            is ValidationResult.Success -> {
                viewModelScope.launch {
                    taskRepository.insertTask(task)
                    loadTasks()
                }
                clearAllErrors()
                return true
            }
            is ValidationResult.Failure -> {
                updateErrorStates(validation)
                return false
            }
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task.id)
            loadTasks()
        }
    }

    fun updateTask(taskToUpdate: Task) {
        when (val validation = validationTask.validate(taskToUpdate)) {
            is ValidationResult.Success -> {
                viewModelScope.launch {
                    taskRepository.updateTask(taskToUpdate)
                    loadTasks()
                }
                clearAllErrors()
            }
            is ValidationResult.Failure -> {
                updateErrorStates(validation)
            }
        }
    }

    fun toggleTaskCompletion(task: Task) {
        val taskToUpdate = task.copy(isCompleted = !task.isCompleted)
        viewModelScope.launch {
            taskRepository.updateTask(taskToUpdate)
            loadTasks()
        }
    }

    private fun updateErrorStates(validation: ValidationResult.Failure) {
        val titleMsg = validation.errors.find { it.field == Field.TITLE }?.message
        val descriptionMsg = validation.errors.find { it.field == Field.DESCRIPTION }?.message

        _titleError.value = titleMsg
        _descriptionError.value = descriptionMsg

        if (titleMsg == null && descriptionMsg == null) {
            _validationError.value = validation.errors.firstOrNull()?.message
        } else {
            _validationError.value = null
        }
    }

    private fun clearAllErrors() {
        _titleError.value = null
        _descriptionError.value = null
        _validationError.value = null
    }

    fun clearValidationError() {
        clearAllErrors()
    }
}
