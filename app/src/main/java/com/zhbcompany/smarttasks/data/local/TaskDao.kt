package com.zhbcompany.smarttasks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhbcompany.smarttasks.data.local.dto.TaskLocal
import java.time.LocalDate

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getTasks(): List<TaskLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTasks(tasks: List<TaskLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: TaskLocal)

    /**
     * Updates an existing task in the local database.
     *
     * @param id The unique identifier of the task to update.
     * @param title The new title of the task.
     * @param description The new description of the task.
     * @param dueDate The new due date of the task. Can be null if not specified.
     * @param targetDate The new target date of the task. Can be null if not specified.
     * @param priority The new priority of the task.
     *
     * @return Nothing. The function is a suspend function, so it can be used in a coroutine.
     */
    @Query("""
        UPDATE task
        SET title = :title,
            description = :description,
            dueDate = :dueDate,
            targetDate = :targetDate,
            priority = :priority
        WHERE id = :id
    """)
    suspend fun updateTask(
        id: String,
        title: String,
        description: String,
        dueDate: LocalDate?,
        targetDate: LocalDate?,
        priority: Int
    )
}