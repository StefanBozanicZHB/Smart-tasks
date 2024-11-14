package com.zhbcompany.smarttasks.domain.use_case

import com.zhbcompany.smarttasks.domain.model.TaskDomain
import com.zhbcompany.smarttasks.domain.repo.TaskRepository
import com.zhbcompany.smarttasks.domain.util.SortingDirection
import com.zhbcompany.smarttasks.domain.util.TaskItemOrder
import com.zhbcompany.smarttasks.domain.util.TaskUseCaseResult
import java.time.LocalDate

/**
 * A class that encapsulates the use cases related to tasks.
 *
 * @param repository The repository to fetch and save tasks.
 */
class TaskUseCases(
    private val repository: TaskRepository
) {
    /**
     * Retrieves tasks based on the target date and sorts them according to the provided order.
     *
     * @param targetDate The date for which tasks are to be retrieved.
     * @param taskItemOrder The order in which tasks should be sorted. Defaults to [TaskItemOrder.Priority] with [SortingDirection.Down].
     *
     * @return A [TaskUseCaseResult] containing a list of tasks if successful, or an error if something went wrong.
     */
    suspend fun getTasks(
        targetDate: LocalDate,
        taskItemOrder: TaskItemOrder = TaskItemOrder.Priority(SortingDirection.Down)
    ): TaskUseCaseResult {
        val tasks = repository.getTasks()

        val filteredTasks = tasks.filter { task ->
            task.targetDate?.let { it <= targetDate } ?: false
        }

        val sortedTasks = when (taskItemOrder) {
            is TaskItemOrder.Priority -> {
                val comparator = if (taskItemOrder.sortingDirection is SortingDirection.Down) {
                    compareByDescending<TaskDomain> { it.priority }
                        .thenBy { it.targetDate ?: LocalDate.MAX }
                } else {
                    compareBy<TaskDomain> { it.priority }
                        .thenBy { it.targetDate ?: LocalDate.MAX }
                }

                filteredTasks.sortedWith(comparator)
            }
        }

        return TaskUseCaseResult.Success(sortedTasks)
    }

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id The unique identifier of the task.
     *
     * @return The task if found, or null if not found.
     */
    suspend fun getTaskById(id: String): TaskDomain? {
        return repository.getTaskById(id)
    }

    /**
     * Saves a task to the repository.
     *
     * @param task The task to be saved.
     */
    suspend fun saveTask(task: TaskDomain) {
        repository.saveTask(task = task)
    }
}