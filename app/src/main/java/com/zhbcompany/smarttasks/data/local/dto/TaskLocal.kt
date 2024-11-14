package com.zhbcompany.smarttasks.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhbcompany.smarttasks.domain.model.TaskStatus
import java.time.LocalDate

@Entity(tableName = "task")
data class TaskLocal(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val dueDate: LocalDate? = null,
    val targetDate: LocalDate? = null,
    val priority: Int,
    val status: TaskStatus = TaskStatus.UNRESOLVED,
    val comment: String = "",
)