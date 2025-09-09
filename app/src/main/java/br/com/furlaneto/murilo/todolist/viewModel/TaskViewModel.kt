package br.com.furlaneto.murilo.todolist.viewModel

import androidx.lifecycle.ViewModel
import br.com.furlaneto.murilo.todolist.model.Task
import br.com.furlaneto.murilo.todolist.services.validation.ValidationResult
import br.com.furlaneto.murilo.todolist.services.validation.ValidationTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()

    fun addTask(task: Task) {
        when(val validation = ValidationTask().validate(task)) {
            is ValidationResult.Success -> {
                _tasks.update { currentList ->
                    currentList + task
                }
                _validationError.value = null
            }
            is ValidationResult.Failure -> {
                _validationError.value = validation.errors.firstOrNull()?.message
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
            _validationError.value = validation.errors.firstOrNull()?.message
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
        _validationError.value = null
    }

    fun clearValidationError(){
        _validationError.value = null
    }
}
