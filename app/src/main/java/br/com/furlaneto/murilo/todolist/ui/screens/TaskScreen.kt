import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            FloatingActionButton(
                onClick = onNavigateToCreateTask,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(50)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Tarefa")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            validationError?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                taskViewModel.clearValidationError()
                LaunchedEffect(errorMsg) {
                    delay(3000)
                    taskViewModel.clearValidationError()
                }
            }

            if (tasks.isNotEmpty()) {
                LazyColumn(modifier = Modifier
                        .fillMaxSize(), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(items = tasks, key = { it.id }) { task ->
                        TaskCard(task = task, taskViewModel)
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
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, viewModel: TaskViewModel) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Medium,
                        color = if (task.isCompleted)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = { isChecked ->
                            viewModel.updateTask(task.copy(isCompleted = isChecked))
                        },
                        modifier = Modifier.padding(end = 4.dp)
                    )

                    if (!task.isCompleted) {
                        IconButton(onClick = { viewModel.removeTask(task) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remover Tarefa",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            if (task.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


