package com.zhbcompany.smarttasks.domain.model

import java.time.LocalDate

/**
 * Represents a task in the application.
 *
 * @param id Unique identifier for the task.
 * @param title Title of the task.
 * @param description Description of the task.
 * @param dueDate Due date of the task. Optional, can be null.
 * @param targetDate Target date of the task. Optional, can be null.
 * @param priority Priority of the task. Higher values indicate higher priority.
 * @param status Status of the task. Defaults to [TaskStatus.UNRESOLVED].
 * @param comment Comment related to the task. Optional, can be empty.
 */
data class TaskDomain(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: LocalDate? = null,
    val targetDate: LocalDate? = null,
    val priority: Int,
    val status: TaskStatus = TaskStatus.UNRESOLVED,
    val comment: String = "",
)
val dummyTask = TaskDomain(
    id = "1",
    title = "Sample Task Title",
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    dueDate = LocalDate.of(2024, 12, 31),
    targetDate = LocalDate.of(2024, 12, 25),
    priority = 1,
    status = TaskStatus.UNRESOLVED,
    comment = "No comments yet"
)