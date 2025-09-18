package br.com.furlaneto.murilo.todolist.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.furlaneto.murilo.todolist.data.datasource.local.dao.TaskDao
import br.com.furlaneto.murilo.todolist.data.datasource.local.entities.TaskEntity


@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase  : RoomDatabase(){
    abstract fun taskDao() : TaskDao

}