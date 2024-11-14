package com.zhbcompany.smarttasks.domain.util

import com.zhbcompany.smarttasks.domain.model.TaskDomain

/**
 * Represents the result of a task-related use case.
 *
 * This sealed class is used to encapsulate the possible outcomes of a task-related use case.
 * It can either be a successful result containing a list of [TaskDomain] objects or an error result
 * containing an error message.
 */
sealed class TaskUseCaseResult {
    /**
     * Represents a successful result of a task-related use case.
     *
     * @param tasks A list of [TaskDomain] objects representing the tasks returned by the use case.
     */
    data class Success(val tasks: List<TaskDomain>) : TaskUseCaseResult()

    /**
     * Represents an error result of a task-related use case.
     *
     * @param message A string containing the error message.
     */
    data class Error(val message: String) : TaskUseCaseResult()
}