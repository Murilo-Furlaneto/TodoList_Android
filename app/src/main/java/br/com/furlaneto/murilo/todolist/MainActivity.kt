package br.com.furlaneto.murilo.todolist

import TaskScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.furlaneto.murilo.todolist.ui.screens.CompletedTaskScreen
import br.com.furlaneto.murilo.todolist.ui.screens.CreateTask
import br.com.furlaneto.murilo.todolist.ui.theme.TodoListTheme
import br.com.furlaneto.murilo.todolist.viewModel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
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
    val taskViewModel: TaskViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "taskList") {
        composable("taskList") {
            TaskScreen(
                taskViewModel = taskViewModel,
                onNavigateToCreateTask = {
                    navController.navigate("createTask")
                },
                navController = navController
            )
        }
        composable("createTask") {
            CreateTask(
                taskViewModel = taskViewModel,
                onNavigateBack = { navController.popBackStack() }
            )

        }
        composable("completedTasks") {
            CompletedTaskScreen(
                viewModel = taskViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}


@HiltAndroidApp
class MyApplication : Application()

