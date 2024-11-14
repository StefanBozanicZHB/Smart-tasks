package com.zhbcompany.smarttasks.data.cache

import com.zhbcompany.smarttasks.domain.model.TaskDomain

class TaskCache {
    private var tasks: MutableList<TaskDomain> = mutableListOf()

    fun isEmpty(): Boolean = tasks.isEmpty()

    fun getTasks(): List<TaskDomain> = tasks

    fun saveTasks(newTasks: List<TaskDomain>) {
        this.tasks.clear()
        this.tasks.addAll(newTasks)
    }

    fun saveTask(task: TaskDomain) {
        val existingTaskIndex = tasks.indexOfFirst { it.id == task.id }
        if (existingTaskIndex != -1) {
            tasks[existingTaskIndex] = task
        } else {
            tasks.add(task)
        }
    }
}