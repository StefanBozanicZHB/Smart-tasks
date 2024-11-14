package com.zhbcompany.smarttasks.data.util

import androidx.room.TypeConverter
import com.zhbcompany.smarttasks.domain.model.TaskStatus

class TaskStatusConverter {

    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String {
        return status.value
    }

    @TypeConverter
    fun toTaskStatus(value: String): TaskStatus {
        return when (value) {
            TaskStatus.RESOLVED.value -> TaskStatus.RESOLVED
            TaskStatus.CANT_RESOLVE.value -> TaskStatus.CANT_RESOLVE
            else -> TaskStatus.UNRESOLVED
        }
    }
}