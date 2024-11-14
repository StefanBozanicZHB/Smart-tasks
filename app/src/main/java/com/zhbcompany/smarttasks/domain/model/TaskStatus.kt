package com.zhbcompany.smarttasks.domain.model

/**
 * Represents the status of a task in the application.
 *
 * @property value The string representation of the task status.
 */
enum class TaskStatus(val value: String) {
    /**
     * Represents an unresolved task.
     */
    UNRESOLVED("Unresolved"),

    /**
     * Represents a resolved task.
     */
    RESOLVED("Resolved"),

    /**
     * Represents a task that cannot be resolved.
     */
    CANT_RESOLVE("Can't resolve")
}
