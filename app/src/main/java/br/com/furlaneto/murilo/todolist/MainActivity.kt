package br.com.furlaneto.murilo.todolist

import TaskScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.furlaneto.murilo.todolist.ui.screens.CreateTask
import br.com.furlaneto.murilo.todolist.ui.theme.TodoListTheme
import br.com.furlaneto.murilo.todolist.viewModel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val taskViewModel: TaskViewModel = viewModel()

    NavHost(navController = navController, startDestination = "taskList") {
        composable("taskList") {
            TaskScreen(
                taskViewModel = taskViewModel,
                onNavigateToCreateTask = {
                    navController.navigate("createTask")
                }
            )
        }
        composable("createTask") {
            CreateTask(
                taskViewModel = taskViewModel,
                onNavigateBack = { navController.popBackStack() }
            )

        }
    }
}
    