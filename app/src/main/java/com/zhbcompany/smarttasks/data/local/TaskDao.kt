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