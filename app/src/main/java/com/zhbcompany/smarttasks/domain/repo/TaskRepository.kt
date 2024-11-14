package com.zhbcompany.smarttasks.domain.repo

import com.zhbcompany.smarttasks.domain.model.TaskDomain

/**
 * Interface for interacting with the task data source.
 */
interface TaskRepository {

    /**
     * Retrieves a list of tasks.
     *
     * @param forceUpdate If true, forces a refresh of the data from the data source.
     * @return A list of [TaskDomain] objects representing the tasks.
     */
    suspend fun getTasks(forceUpdate: Boolean = false): List<TaskDomain>

    /**
     * Saves a task to the data source.
     *
     * @param task The [TaskDomain] object to be saved.
     */
    suspend fun saveTask(task: TaskDomain)

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id The unique identifier of the task.
     * @return The [TaskDomain] object representing the task, or null if not found.
     */
    suspend fun getTaskById(id: String): TaskDomain?
}