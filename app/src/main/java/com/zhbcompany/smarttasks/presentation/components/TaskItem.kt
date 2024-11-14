package com.zhbcompany.smarttasks.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhbcompany.smarttasks.R
import com.zhbcompany.smarttasks.domain.model.TaskDomain
import com.zhbcompany.smarttasks.domain.model.TaskStatus
import com.zhbcompany.smarttasks.domain.model.dummyTask
import com.zhbcompany.smarttasks.ui.theme.SmartTasksTheme
import com.zhbcompany.smarttasks.util.DateUtil

@Composable
fun CardTaskItem(
    modifier: Modifier = Modifier,
    task: TaskDomain,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = onCardClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        ShortTaskPreview(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .heightIn(min = 80.dp),
            task = task,
            showStatusIcon = true
        )
    }
}

@Composable
fun ShortTaskPreview(
    modifier: Modifier = Modifier,
    task: TaskDomain,
    titleColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    subtitleColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    showStatusIcon: Boolean = false
) {
    Column(
        modifier = modifier,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = task.title,
                color = titleColor,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if(showStatusIcon && task.status.value != TaskStatus.UNRESOLVED.value) {
                Image(
                    painter = if(task.status.value == TaskStatus.RESOLVED.value) painterResource(id = R.drawable.btn_resolved)
                    else painterResource(id = R.drawable.btn_unresolved),
                    contentDescription = stringResource(R.string.empty_screen),
                    modifier = Modifier.size(25.dp).padding(start = 5.dp)
                )
            }
        }
        TaskDivider(
            spaceBefore = 7.dp,
            spaceAfter = 10.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.due_date),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.labelMedium,
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = DateUtil.fromDate(task.dueDate) ?: "#",
                    color = subtitleColor,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                task.dueDate?.let { dueDate ->
                    Text(
                        text = stringResource(R.string.days_left),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Spacer(Modifier.height(7.dp))
                    Text(
                        text = DateUtil.daysLeftOfCurrentDay(dueDate).toString(),
                        color = subtitleColor,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}

@Composable
fun LongTaskPreview(
    modifier: Modifier = Modifier,
    task: TaskDomain,
    titleColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    subtitleColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    statusColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    Column(
        modifier = modifier,
    ) {
        ShortTaskPreview(
            task = task,
            titleColor = titleColor,
            subtitleColor = subtitleColor,
        )
        TaskDivider()
        Text(
            text = task.description,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.labelMedium,
        )
        if (task.comment.isNotBlank()) {
            TaskDivider()
            Text(
                text = stringResource(R.string.your_comment) + ":",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = task.comment,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.labelMedium,
            )
        }
        TaskDivider()
        Text(
            text = task.status.value,
            color = statusColor,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShortTaskPreview_Preview() {
    SmartTasksTheme {
        ShortTaskPreview(
            task = dummyTask,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LongTaskPreview_Preview() {
    SmartTasksTheme {
        LongTaskPreview(
            task = dummyTask,
        )
    }
}