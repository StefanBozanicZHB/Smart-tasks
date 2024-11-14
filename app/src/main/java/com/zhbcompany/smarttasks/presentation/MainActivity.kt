package com.zhbcompany.smarttasks.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zhbcompany.smarttasks.presentation.task_list.TaskListScreen
import com.zhbcompany.smarttasks.presentation.task_list.TaskListViewModel
import com.zhbcompany.smarttasks.ui.theme.SmartTasksTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartTasksTheme {
                val taskListViewModel : TaskListViewModel = getViewModel()

                TaskListScreen(
                    viewModel = taskListViewModel
                )
            }
        }
    }
}