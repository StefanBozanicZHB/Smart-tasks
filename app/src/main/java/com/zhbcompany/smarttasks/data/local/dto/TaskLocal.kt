package com.zhbcompany.smarttasks.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhbcompany.smarttasks.domain.model.TaskStatus
import java.time.LocalDate

/**
 * Represents a task entity stored locally in the database.
 *
 * @property id The unique identifier of the task.
 * @property title The title of the task.
 * @property description The detailed description of the task.
 * @property dueDate The due date of the task, nullable.
 * @property targetDate The target date of the task, nullable.
 * @property priority The priority of the task, represented as an integer.
 * @property status The status of the task, defaults to [TaskStatus.UNRESOLVED].
 * @property comment Additional comments related to the task, defaults to an empty string.
 */
@Entity(tableName = "task")
data class TaskLocal(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val dueDate: LocalDate? = null,
    val targetDate: LocalDate? = null,
    val priority: Int,
    val status: TaskStatus = TaskStatus.UNRESOLVED,
    val comment: String = "",
)