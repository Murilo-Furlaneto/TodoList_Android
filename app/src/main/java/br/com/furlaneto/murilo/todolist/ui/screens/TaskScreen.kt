package br.com.furlaneto.murilo.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.furlaneto.murilo.todolist.model.Task
import br.com.furlaneto.murilo.todolist.viewModel.TaskViewModel
import kotlinx.coroutines.delay

@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel,
    onNavigateToCreateTask: () -> Unit
) {
    val tasks by taskViewModel.taskList.collectAsState()
    val validationError by taskViewModel.validationError.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreateTask) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Tarefa")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            validationError?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                 taskViewModel.clearValidationError()
                LaunchedEffect(errorMsg) {
                    delay(3000)
                    taskViewModel.clearValidationError()
                }
            }

            if (tasks.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = tasks, key = { task -> task.id }) { task ->
                        TaskItemPlaceholder(task = task)
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhuma tarefa encontrada.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItemPlaceholder(task: Task) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "Título: ${task.title}",
            style = MaterialTheme.typography.titleMedium
        )
        if (task.description.isNotBlank()) {
            Text(
                text = "Descrição: ${task.description}",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(
            text = "Completa: ${task.isCompleted}",
            style = MaterialTheme.typography.bodySmall,
            color = if (task.isCompleted) Color.Green else MaterialTheme.colorScheme.onSurface
        )
    }
}


