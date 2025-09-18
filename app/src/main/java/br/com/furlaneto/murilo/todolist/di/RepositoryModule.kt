package br.com.furlaneto.murilo.todolist.di

import br.com.furlaneto.murilo.todolist.data.datasource.local.dao.TaskDao
import br.com.furlaneto.murilo.todolist.data.repository.TaskRepository
import br.com.furlaneto.murilo.todolist.data.repository.TaskRepositoryImpl
import br.com.furlaneto.murilo.todolist.services.validation.ValidationTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao,
    ): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

}