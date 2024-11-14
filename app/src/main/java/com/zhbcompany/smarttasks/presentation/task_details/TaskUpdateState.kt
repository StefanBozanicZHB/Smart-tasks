package com.zhbcompany.smarttasks.presentation.task_details

import com.zhbcompany.smarttasks.domain.model.TaskDomain

/**
 * Represents the different states of a task update operation in the Task Details screen.
 *
 * This sealed class is used to handle the different possible outcomes of the task update process.
 * It includes three subclasses: Loading, Success, and Error.
 *
 * - Loading: Indicates that the task update operation is in progress.
 * - Success: Contains the updated task data when the operation is successful.
 * - Error: Contains an error message when the operation fails.
 */
sealed class TaskUpdateState {
    /**
     * Represents the loading state of the task update operation.
     *
     * This object is used when the task update process is in progress.
     */
    object Loading : TaskUpdateState()

    /**
     * Represents the successful state of the task update operation.
     *
     * This data class contains the updated task data when the operation is successful.
     *
     * @param task The updated task data.
     */
    data class Success(val task: TaskDomain) : TaskUpdateState()

    /**
     * Represents the error state of the task update operation.
     *
     * This data class contains an error message when the operation fails.
     *
     * @param message The error message.
     */
    data class Error(val message: String) : TaskUpdateState()
}