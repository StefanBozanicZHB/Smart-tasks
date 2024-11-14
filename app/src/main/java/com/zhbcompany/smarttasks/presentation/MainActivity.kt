package com.zhbcompany.smarttasks.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zhbcompany.smarttasks.presentation.task_details.TaskDetailsScreen
import com.zhbcompany.smarttasks.presentation.task_details.TaskDetailsViewModel
import com.zhbcompany.smarttasks.presentation.task_list.TaskListScreen
import com.zhbcompany.smarttasks.presentation.task_list.TaskListViewModel
import com.zhbcompany.smarttasks.ui.theme.SmartTasksTheme
import com.zhbcompany.smarttasks.presentation.util.Screen
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartTasksTheme {
                val navController = rememberNavController()

                val taskListViewModel : TaskListViewModel = getViewModel()
                val taskDetailsViewModel : TaskDetailsViewModel = getViewModel()

                NavHost(
                    navController = navController,
                    startDestination = Screen.TaskListScreen.route
                ) {
                    composable(route = Screen.TaskListScreen.route) {
                        TaskListScreen(
                            navController = navController,
                            viewModel = taskListViewModel
                        )
                    }

                    composable(
                        route = Screen.TaskDetailsScreen.route + "?taskId={taskId}",
                        arguments = listOf(
                            navArgument(
                                name = "taskId",
                            ) {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        TaskDetailsScreen(
                            navController = navController,
                            viewModel = taskDetailsViewModel,
                            backStackEntry.arguments?.getString("taskId"),
                        )
                    }
                }
            }
        }
    }
}