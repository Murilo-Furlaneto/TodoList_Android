package br.com.furlaneto.murilo.todolist.model

data class Task(
    val id: Long,
    var title: String,
    var description: String,
    var isCompleted: Boolean = false
)
