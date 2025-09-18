package br.com.furlaneto.murilo.todolist.data.repository

import br.com.furlaneto.murilo.todolist.model.Task

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun getTaskById(id: Long): Task?
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: Long)
    suspend fun getCompletedTasks(): List<Task>
    suspend fun markTaskAsCompleted(taskId: Long)

}
