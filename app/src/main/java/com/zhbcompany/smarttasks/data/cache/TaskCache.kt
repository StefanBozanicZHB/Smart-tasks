package com.zhbcompany.smarttasks.data.cache

import com.zhbcompany.smarttasks.domain.model.TaskDomain

class TaskCache {
    private var tasks: MutableList<TaskDomain> = mutableListOf()

    /**
     * Checks if the cache is empty.
     *
     * @return true if the cache is empty, false otherwise.
     */
    fun isEmpty(): Boolean = tasks.isEmpty()

    /**
     * Retrieves all tasks from the cache.
     *
     * @return a list of [TaskDomain] objects representing the tasks in the cache.
     */
    fun getTasks(): List<TaskDomain> = tasks

    /**
     * Saves a list of tasks to the cache.
     *
     * @param newTasks a list of [TaskDomain] objects representing the tasks to be saved.
     * The existing tasks in the cache will be cleared and replaced with the new tasks.
     */
    fun saveTasks(newTasks: List<TaskDomain>) {
        this.tasks.clear()
        this.tasks.addAll(newTasks)
    }

    /**
     * Saves a single task to the cache.
     *
     * If a task with the same ID already exists in the cache, it will be updated with the new task.
     * If no task with the same ID exists, the new task will be added to the cache.
     *
     * @param task a [TaskDomain] object representing the task to be saved.
     */
    fun saveTask(task: TaskDomain) {
        val existingTaskIndex = tasks.indexOfFirst { it.id == task.id }
        if (existingTaskIndex != -1) {
            tasks[existingTaskIndex] = task
        } else {
            tasks.add(task)
        }
    }
}