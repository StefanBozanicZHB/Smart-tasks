package com.zhbcompany.smarttasks.domain.util

/**
 * Represents the direction in which a list of items can be sorted.
 *
 * This sealed class is used to define two possible sorting directions: [Up] and [Down].
 *
 * @author Stefan Bozanic
 */
sealed class SortingDirection {
    /**
     * Represents an ascending sorting order.
     */
    object Up : SortingDirection()

    /**
     * Represents a descending sorting order.
     */
    object Down : SortingDirection()
}