package com.zhbcompany.smarttasks.presentation.task_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhbcompany.smarttasks.R
import com.zhbcompany.smarttasks.domain.model.TaskDomain
import com.zhbcompany.smarttasks.domain.model.TaskStatus
import com.zhbcompany.smarttasks.domain.model.dummyTask
import com.zhbcompany.smarttasks.presentation.components.AppBarIcon
import com.zhbcompany.smarttasks.presentation.components.AppBarTitle
import com.zhbcompany.smarttasks.presentation.components.CenteredErrorMessage
import com.zhbcompany.smarttasks.presentation.components.LoadingIndicator
import com.zhbcompany.smarttasks.presentation.components.LongTaskPreview
import com.zhbcompany.smarttasks.presentation.components.NotepadView
import com.zhbcompany.smarttasks.ui.theme.SmartTasksTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    navController: NavController,
    viewModel: TaskDetailsViewModel,
    taskId: String?,
) {
    val state = viewModel.state.collectAsState()
    val commentInputText = viewModel.commentInputText.collectAsState()
    val showCommentQuestionDialog = viewModel.showCommentQuestionDialog.collectAsState()
    val showCommentInputDialog = viewModel.showCommentInputDialog.collectAsState()

    LaunchedEffect(Unit) {
        if (taskId != null) {
            viewModel.loadTask(taskId)
        }

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                TaskDetailsViewModel.UiEvent.Back -> navController.navigateUp()
                TaskDetailsViewModel.UiEvent.SaveStatusTask -> viewModel.showCommentQuestionDialog()
                TaskDetailsViewModel.UiEvent.SaveCommentTask -> navController.navigateUp()
                is TaskDetailsViewModel.UiEvent.ResponseCommentQuestion -> if (event.answer) {
                    viewModel.showCommentInputDialog()
                } else {
                    navController.navigateUp()
                }
                TaskDetailsViewModel.UiEvent.CancelInputCommentTask -> navController.navigateUp()
            }
        }
    }
    if (showCommentQuestionDialog.value) {
        CommentQuestionAlertDialog(viewModel)
    }
    if (showCommentInputDialog.value) {
        CommentInputAlertDialog(viewModel, commentInputText.value)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { AppBarTitle(text = stringResource(R.string.task_detail)) },
                navigationIcon = {
                    AppBarIcon(
                        onClick = { viewModel.onBack() },
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back),
                    )
                },
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                when (val currentState = state.value) {
                    is TaskUpdateState.Loading -> LoadingIndicator()
                    is TaskUpdateState.Success -> TaskDetails(
                        task = currentState.task,
                        onResolveClick = { viewModel.resolveTask() },
                        onCannotResolveClick = { viewModel.cantResolveTask() }
                    )
                    is TaskUpdateState.Error -> CenteredErrorMessage(currentState.message)
                }
            }
        }
    )
}

@Composable
fun CommentQuestionAlertDialog(
    viewModel: TaskDetailsViewModel
) {
    AlertDialog(
        onDismissRequest = { viewModel.onQuestionDialogResponse(false) },
        title = { Text(stringResource(R.string.question_comment)) },
        confirmButton = {
            TextButton(
                onClick = { viewModel.onQuestionDialogResponse(true) }
            ) {
                Text(stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.onQuestionDialogResponse(false) }
            ) {
                Text(stringResource(R.string.no))
            }
        }
    )
}

@Composable
fun CommentInputAlertDialog(
    viewModel: TaskDetailsViewModel,
    commentInputText: String,
) {
    AlertDialog(
        onDismissRequest = { viewModel.onInputDialogResponse(false) },
        title = { Text(stringResource(R.string.your_comment)) },
        text = {
            OutlinedTextField(
                value = commentInputText,
                onValueChange = {
                    viewModel.onInputTextChange(it)
                },
            )
        },
        confirmButton = {
            TextButton(
                onClick = { viewModel.onInputDialogResponse(true) }
            ) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.onInputDialogResponse(false) }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun TaskDetails(
    modifier: Modifier = Modifier,
    task: TaskDomain,
    onResolveClick: () -> Unit,
    onCannotResolveClick: () -> Unit,
) {
    val (statusColor, titleColor) = when (task.status) {
        TaskStatus.UNRESOLVED -> Pair(
            MaterialTheme.colorScheme.onTertiary,
            MaterialTheme.colorScheme.onPrimaryContainer
        )

        TaskStatus.RESOLVED -> Pair(
            MaterialTheme.colorScheme.onSecondary,
            MaterialTheme.colorScheme.onSecondary
        )

        else -> Pair(
            MaterialTheme.colorScheme.onPrimaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NotepadView(
            pillarColor = statusColor,
            backgroundColor = Color.White
        ) {
            LongTaskPreview(
                modifier = Modifier.padding(all = 10.dp),
                task = task,
                titleColor = titleColor,
                subtitleColor = titleColor,
                statusColor = statusColor,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (task.status) {
            TaskStatus.RESOLVED -> {
                StatusImage(
                    painter = painterResource(id = R.drawable.sign_resolved),
                    contentDescription = stringResource(R.string.resolved)
                )
            }

            TaskStatus.CANT_RESOLVE -> {
                StatusImage(
                    painter = painterResource(id = R.drawable.unresolved_sign),
                    contentDescription = stringResource(R.string.unresolved)
                )
            }

            else -> {
                TaskActions(
                    onResolveClick = onResolveClick,
                    onCannotResolveClick = onCannotResolveClick
                )
            }
        }
    }
}

@Composable
fun StatusImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    size: Dp = 100.dp,
) {
    Image(
        modifier = modifier.size(size),
        painter = painter,
        contentDescription = contentDescription,
    )
}

@Composable
fun TaskActions(
    onResolveClick: () -> Unit = {},
    onCannotResolveClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        TaskAction(
            modifier = Modifier
                .weight(1f),
            title = stringResource(R.string.resolve),
            color = MaterialTheme.colorScheme.primary,
            onClick = onResolveClick
        )
        TaskAction(
            modifier = Modifier
                .weight(1f),
            title = stringResource(R.string.can_not_resolve),
            color = MaterialTheme.colorScheme.errorContainer,
            onClick = onCannotResolveClick
        )
    }
}

@Composable
fun TaskAction(
    modifier: Modifier = Modifier,
    title: String,
    color: Color,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnresolvedTaskDetailsPreview() {
    SmartTasksTheme {
        TaskDetails(
            task = dummyTask.copy(
                status = TaskStatus.UNRESOLVED
            ),
            onResolveClick = {},
            onCannotResolveClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResolvedTaskDetailsPreview() {
    SmartTasksTheme {
        TaskDetails(
            task = dummyTask.copy(
                status = TaskStatus.RESOLVED
            ),
            onResolveClick = {},
            onCannotResolveClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CantResolveTaskDetailsPreview() {
    SmartTasksTheme {
        TaskDetails(
            task = dummyTask.copy(
                status = TaskStatus.CANT_RESOLVE
            ),
            onResolveClick = {},
            onCannotResolveClick = {}
        )
    }
}