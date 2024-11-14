package com.zhbcompany.smarttasks.domain.util

/**
 * Represents different ways to order task items.
 *
 * @param sortingDirection The direction in which the task items should be sorted.
 */
sealed class TaskItemOrder(
    val sortingDirection: SortingDirection,
) {
    /**
     * Represents a task item order based on priority.
     *
     * @param sortingDirection The direction in which the task items should be sorted.
     */
    class Priority(sortingDirection: SortingDirection) : TaskItemOrder(sortingDirection)

    /**
     * Creates a copy of the current [TaskItemOrder] with the specified [sortingDirection].
     *
     * @param sortingDirection The new sorting direction for the copied [TaskItemOrder].
     * @return A new [TaskItemOrder] with the specified [sortingDirection].
     */
    fun copy(sortingDirection: SortingDirection): TaskItemOrder {
        return when (this) {
            is Priority -> Priority(sortingDirection)
        }
    }
}