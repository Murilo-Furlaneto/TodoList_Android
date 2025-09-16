package br.com.furlaneto.murilo.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.furlaneto.murilo.todolist.model.Task
import br.com.furlaneto.murilo.todolist.viewModel.TaskViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTask(
    onNavigateBack: () -> Unit,
    taskViewModel: TaskViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val titleErrorFromViewModel by taskViewModel.titleError.collectAsState()
    val descriptionErrorFromViewModel by taskViewModel.descriptionError.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Tarefa") },
                navigationIcon = {
                    IconButton(onClick = {
                        taskViewModel.clearValidationError()
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Crie uma nova tarefa",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (titleErrorFromViewModel != null) {
                        taskViewModel.clearValidationError()
                    }
                },
                label = { Text("Título da Tarefa") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = titleErrorFromViewModel != null
            )

            titleErrorFromViewModel?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    if (descriptionErrorFromViewModel != null) {
                        taskViewModel.clearValidationError()
                    }
                },
                label = { Text("Descrição (Opcional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5,
                isError = descriptionErrorFromViewModel != null
            )

            descriptionErrorFromViewModel?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start).padding(top = 4.dp)
                )
            }


            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val newTask = Task(
                        id = Random.nextLong(),
                        title = title.trim(),
                        description = description.trim(),
                        isCompleted = false
                    )
                    val success = taskViewModel.addTask(newTask)
                    if (success) {
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
