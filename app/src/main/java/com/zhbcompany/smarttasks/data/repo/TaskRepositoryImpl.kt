package com.zhbcompany.smarttasks.data.repo

import com.zhbcompany.smarttasks.data.cache.TaskCache
import com.zhbcompany.smarttasks.data.local.TaskDao
import com.zhbcompany.smarttasks.data.mapper.toDomainTaskItemsFromLocal
import com.zhbcompany.smarttasks.data.mapper.toDomainTaskItemsFromRemote
import com.zhbcompany.smarttasks.data.mapper.toLocalTask
import com.zhbcompany.smarttasks.data.remote.TaskApiService
import com.zhbcompany.smarttasks.domain.model.TaskDomain
import com.zhbcompany.smarttasks.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

class TaskRepositoryImpl(
    private val dao: TaskDao,
    private val api: TaskApiService,
    private val cache: TaskCache,
    private val dispatcher: CoroutineDispatcher,
) : TaskRepository {

    override suspend fun getTasks(forceUpdate: Boolean): List<TaskDomain> {
        return if (forceUpdate || cache.isEmpty()) {
            try {
                getTasksFromRemote()
                cache.getTasks()
            } catch (exception: Exception) {
                dao.getTasks().toDomainTaskItemsFromLocal()
            }
        } else {
            cache.getTasks()
        }
    }

    private suspend fun getTasksFromRemote() {
        return withContext(dispatcher) {
            try {
                val tasks = api.fetchTasks().tasks.toDomainTaskItemsFromRemote()
                val existingTaskMap = dao.getTasks().associateBy { it.id }
                tasks.forEach { task ->
                    val existingTask = existingTaskMap[task.id]
                    if (existingTask == null) {
                        dao.saveTask(task.toLocalTask())
                    } else {
                        dao.updateTask(
                            id = task.id,
                            title = task.title,
                            description = task.description,
                            dueDate = task.dueDate,
                            targetDate = task.targetDate,
                            priority = task.priority
                        )
                    }
                }
                cache.saveTasks(dao.getTasks().toDomainTaskItemsFromLocal())
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException, is ConnectException, is HttpException -> {
                        Timber.e("No data from Remote")
                        val localTaskItems = dao.getTasks()
                        if (localTaskItems.isNotEmpty()) {
                            cache.saveTasks(localTaskItems.toDomainTaskItemsFromLocal())
                        } else {
                            Timber.e("No data from local Room cache")
                            throw Exception("Error: Device offline and\nno data from local Room cache")
                        }
                    }

                    else -> throw e
                }
            }
        }
    }

    override suspend fun saveTask(task: TaskDomain) {
        cache.saveTask(task)
        dao.saveTask(task.toLocalTask())
    }

    override suspend fun getTaskById(id: String): TaskDomain? {
        return cache.getTasks().find { it.id == id }
    }
}