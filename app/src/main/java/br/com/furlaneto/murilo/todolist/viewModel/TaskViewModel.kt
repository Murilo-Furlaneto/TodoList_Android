package br.com.furlaneto.murilo.todolist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.todolist.model.Task
import br.com.furlaneto.murilo.todolist.services.validation.Field
import br.com.furlaneto.murilo.todolist.services.validation.ValidationResult
import br.com.furlaneto.murilo.todolist.services.validation.ValidationTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.SharingStarted


class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()

    val completedTasks: StateFlow<List<Task>> = _tasks
        .map { list -> list.filter { it.isCompleted } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _titleError = MutableStateFlow<String?>(null)
    val titleError: StateFlow<String?> = _titleError.asStateFlow()

    private val _descriptionError = MutableStateFlow<String?>(null)
    val descriptionError: StateFlow<String?> = _descriptionError.asStateFlow()


    fun addTask(task: Task): Boolean {
        when(val validation = ValidationTask().validate(task)) {
            is ValidationResult.Success -> {
                _tasks.update { currentList -> currentList + task }
                _titleError.value = null
                _descriptionError.value = null
                _validationError.value = null
                return true
            }
            is ValidationResult.Failure -> {
                val titleMsg = validation.errors.find { it.field == Field.TITLE }?.message
                val descriptionMsg = validation.errors.find { it.field == Field.DESCRIPTION }?.message

                _titleError.value = titleMsg
                _descriptionError.value = descriptionMsg


                if (titleMsg == null && descriptionMsg == null) {
                    _validationError.value = validation.errors.firstOrNull()?.message
                } else {
                    _validationError.value = null
                }
                return false
            }
        }
    }

    fun removeTask(task: Task) {
        _tasks.update { currentList ->
            currentList - task
        }
    }

    fun updateTask(taskToUpdate: Task) {
        val validation = ValidationTask().validate(taskToUpdate)
        if (validation is ValidationResult.Failure) {
            _titleError.value = validation.errors.find { it.field == Field.TITLE }?.message
            _descriptionError.value = validation.errors.find { it.field == Field.DESCRIPTION }?.message
            if (_titleError.value == null && _descriptionError.value == null) {
                 _validationError.value = validation.errors.firstOrNull()?.message
            } else {
                _validationError.value = null
            }
            return
        }
        _tasks.update { currentList ->
            currentList.map { existingTask ->
                if (existingTask.id == taskToUpdate.id) {
                    taskToUpdate
                } else {
                    existingTask
                }
            }
        }
        _titleError.value = null
        _descriptionError.value = null
        _validationError.value = null
    }

    fun clearValidationError(){
        _validationError.value = null
        _titleError.value = null
        _descriptionError.value = null
    }

    fun toggleTaskCompletion(task: Task) {
        val taskToUpdate = task.copy(isCompleted = !task.isCompleted)
        val actualList = _tasks.value
        val exists = actualList.any { it.id == task.id }

        if (exists) {
            updateTask(taskToUpdate)
        }
    }
}
