package com.zhbcompany.smarttasks.presentation.task_list

import com.zhbcompany.smarttasks.domain.model.TaskDomain

/**
 * Represents the different states of the task list screen.
 *
 * This sealed class is used to handle the different states of the task list UI, such as loading, success, and error.
 *
 * @see Loading
 * @see Success
 * @see Error
 */
sealed class TaskListState {

    /**
     * Represents the loading state of the task list.
     *
     * This state is used when the task list is being fetched from the data source.
     */
    object Loading : TaskListState()

    /**
     * Represents the success state of the task list.
     *
     * This state is used when the task list has been successfully fetched from the data source.
     *
     * @param tasks The list of tasks that have been fetched.
     */
    data class Success(val tasks: List<TaskDomain>) : TaskListState()

    /**
     * Represents the error state of the task list.
     *
     * This state is used when an error occurs while fetching the task list from the data source.
     *
     * @param message The error message that describes the error.
     */
    data class Error(val message: String) : TaskListState()
}