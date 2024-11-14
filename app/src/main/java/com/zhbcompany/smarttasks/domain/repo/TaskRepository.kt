package com.zhbcompany.smarttasks.domain.repo

import com.zhbcompany.smarttasks.domain.model.TaskDomain

interface TaskRepository {
    suspend fun getTasks(forceUpdate: Boolean = false): List<TaskDomain>
    suspend fun saveTask(task: TaskDomain)
    suspend fun getTaskById(id: String): TaskDomain?
}