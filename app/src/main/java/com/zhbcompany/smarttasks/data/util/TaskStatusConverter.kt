package com.zhbcompany.smarttasks.data.util

import androidx.room.TypeConverter
import com.zhbcompany.smarttasks.domain.model.TaskStatus

/**
 * A utility class for converting between [TaskStatus] and its corresponding string representation.
 * This class is used by Room to perform type conversions when storing and retrieving data from the database.
 */
class TaskStatusConverter {

    /**
     * Converts a [TaskStatus] object to its corresponding string representation.
     *
     * @param status The [TaskStatus] object to be converted.
     * @return A string representation of the [TaskStatus].
     */
    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String {
        return status.value
    }

    /**
     * Converts a string representation of a [TaskStatus] to its corresponding [TaskStatus] object.
     *
     * @param value The string representation of the [TaskStatus].
     * @return The corresponding [TaskStatus] object. If the input string does not match any known status,
     * the function returns [TaskStatus.UNRESOLVED].
     */
    @TypeConverter
    fun toTaskStatus(value: String): TaskStatus {
        return when (value) {
            TaskStatus.RESOLVED.value -> TaskStatus.RESOLVED
            TaskStatus.CANT_RESOLVE.value -> TaskStatus.CANT_RESOLVE
            else -> TaskStatus.UNRESOLVED
        }
    }
}