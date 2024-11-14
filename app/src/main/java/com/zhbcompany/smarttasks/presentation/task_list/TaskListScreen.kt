package com.zhbcompany.smarttasks.presentation.task_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zhbcompany.smarttasks.R
import com.zhbcompany.smarttasks.domain.model.TaskDomain
import com.zhbcompany.smarttasks.presentation.components.AppBarIcon
import com.zhbcompany.smarttasks.presentation.components.AppBarTitle
import com.zhbcompany.smarttasks.presentation.components.CardTaskItem
import com.zhbcompany.smarttasks.presentation.components.CenteredErrorMessage
import com.zhbcompany.smarttasks.presentation.components.LoadingIndicator
import com.zhbcompany.smarttasks.util.DateUtil
import java.time.LocalDate

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
) {
    val state = viewModel.state.collectAsState()
    val targetDate = viewModel.targetDate.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.observeTargetDate()
    }

    Scaffold(
        topBar = {
            TaskListAppBar(
                dateText = DateUtil.getAppBarFriendlyDateString(LocalContext.current, targetDate.value),
                onPreviousDayClick = viewModel::moveToPreviousDay,
                onNextDayClick = viewModel::moveToNextDay
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                when (val currentState = state.value) {
                    is TaskListState.Loading -> LoadingIndicator()
                    is TaskListState.Success -> TaskListContent(
                        tasks = currentState.tasks,
                        targetDate = targetDate.value,
                        onCardItemClick = {
                            // todo implement this
                        }
                    )
                    is TaskListState.Error -> CenteredErrorMessage(currentState.message)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListAppBar(
    dateText: String,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit
) {
    TopAppBar(
        title = { AppBarTitle(text = dateText) },
        navigationIcon = {
            AppBarIcon(
                onClick = onPreviousDayClick,
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.previous_day),
            )
        },
        actions = {
            AppBarIcon(
                onClick = onNextDayClick,
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.next_day),
            )
        }
    )
}

@Composable
fun TaskListContent(
    tasks: List<TaskDomain>,
    targetDate: LocalDate,
    onCardItemClick: (String) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyTaskScreen(
            dateText = DateUtil.getContentFriendlyDateString(LocalContext.current, targetDate)
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
        ) {
            items(tasks) { task ->
                CardTaskItem(
                    task = task,
                    onCardClick = {
                        onCardItemClick(task.id)
                    }
                )
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}


@Composable
fun EmptyTaskScreen(
    modifier: Modifier = Modifier,
    dateText: String,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_screen),
                contentDescription = stringResource(R.string.empty_screen)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "${stringResource(R.string.no_tasks)} $dateText!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}