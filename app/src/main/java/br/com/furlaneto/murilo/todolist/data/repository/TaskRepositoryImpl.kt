package br.com.furlaneto.murilo.todolist.data.repository

import br.com.furlaneto.murilo.todolist.data.datasource.local.dao.TaskDao
import br.com.furlaneto.murilo.todolist.data.datasource.local.entities.TaskEntity
import br.com.furlaneto.murilo.todolist.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    private fun TaskEntity.toDomain(): Task = Task(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
    )

    private fun Task.toEntity(): TaskEntity = TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
    )

    override suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map { it.toDomain() }
    }

    override suspend fun getTaskById(id: Long): Task? {
        return taskDao.getTaskById(id)?.toDomain()
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun deleteTask(id: Long) {
        taskDao.deleteTask(id)
    }

    override suspend fun getCompletedTasks(): List<Task> {
        return taskDao.getCompletedTasks().map { it.toDomain() }
    }

    override suspend fun markTaskAsCompleted(taskId: Long) {
        taskDao.markTaskAsCompleted(taskId)
    }
}
