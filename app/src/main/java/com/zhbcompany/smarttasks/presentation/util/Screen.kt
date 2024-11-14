package com.zhbcompany.smarttasks.presentation.util

/**
 * Represents different screens in the application.
 *
 * @param route The unique route identifier for each screen.
 *
 * @author Stefan Bozanic
 */
sealed class Screen(val route: String) {

    /**
     * Represents the Task List screen.
     */
    object TaskListScreen: Screen("task_list_screen")

    /**
     * Represents the Task Details screen.
     */
    object TaskDetailsScreen: Screen("task_details_screen")
}