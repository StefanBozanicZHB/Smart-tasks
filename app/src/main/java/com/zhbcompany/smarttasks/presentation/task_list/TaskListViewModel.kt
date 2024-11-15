package com.zhbcompany.smarttasks.presentation.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhbcompany.smarttasks.domain.use_case.TaskUseCases
import com.zhbcompany.smarttasks.domain.util.TaskUseCaseResult
import com.zhbcompany.smarttasks.util.SetCancelJob
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

class TaskListViewModel(
    private val taskUseCases: TaskUseCases,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    // State management
    private val _state = MutableStateFlow<TaskListState>(TaskListState.Loading)
    val state: StateFlow<TaskListState> get() = _state.asStateFlow()

    // Target date management
    private val _targetDate = MutableStateFlow(LocalDate.now())
    val targetDate: StateFlow<LocalDate> get() = _targetDate.asStateFlow()

    private val _showCalendar = MutableStateFlow(false)
    val showCalendar: StateFlow<Boolean> get() = _showCalendar.asStateFlow()

    // Job management for fetching tasks
    private var getTasksJob by SetCancelJob()

    /**
     * Observes the target date and fetches tasks when it changes.
     *
     * This function launches a coroutine in the ViewModel's scope, which collects the target date from the
     * [targetDate] flow. Whenever a new date is emitted, it calls [fetchTasksForDate] to retrieve tasks for
     * that specific date.
     *
     * @return Nothing.
     */
    fun observeTargetDate() {
        viewModelScope.launch {
            targetDate.collect { date ->
                fetchTasksForDate(date)
            }
        }
    }

    fun changeDate(newDate: LocalDate) {
        _targetDate.value = newDate
    }

    /**
     * Moves the target date to the next day.
     *
     * This function updates the target date by adding one day to the current value of the [targetDate] flow.
     * It does not return any value, but it modifies the state of the [targetDate] flow.
     *
     * @return Nothing.
     */
    fun moveToNextDay() {
        _targetDate.value = _targetDate.value.plusDays(1)
    }

    /**
     * Moves the target date to the previous day.
     *
     * This function updates the target date by subtracting one day from the current value of the
     * [targetDate] flow. It does not return any value, but it modifies the state of the [targetDate] flow.
     *
     * @return Nothing.
     */
    fun moveToPreviousDay() {
        _targetDate.value = _targetDate.value.minusDays(1)
    }

    /**
     * Fetches tasks for a given date.
     *
     * This function is responsible for initiating the process of fetching tasks for a specific date. It first
     * cancels any ongoing job to ensure that only one request is processed at a time. Then, it updates the
     * state of the [state] flow to [TaskListState.Loading] to indicate that tasks are being fetched. After
     * that, it calls the [TaskUseCases.getTasks] function to retrieve tasks for the given date. The result
     * of this operation is then passed to the [handleTaskResult] function for further processing.
     *
     * @param date The date for which tasks should be fetched.
     * @return Nothing.
     */
    private fun fetchTasksForDate(date: LocalDate) {
        // Cancel any ongoing job before starting a new one
        getTasksJob = viewModelScope.launch(dispatcher) {
            _state.value = TaskListState.Loading
    
            val result = taskUseCases.getTasks(date)
            handleTaskResult(result)
        }
    }

    /**
     * Handles the result of fetching tasks.
     *
     * This function processes the result of the [TaskUseCases.getTasks] function,
     * which retrieves tasks for a specific date.
     * Depending on the type of result, it updates the state of the [state] flow accordingly.
     *
     * @param result The result of fetching tasks, which can be either a [TaskUseCaseResult.Success]
     * or a [TaskUseCaseResult.Error].
     *
     * @return Nothing.
     */
    private fun handleTaskResult(result: TaskUseCaseResult) {
        when (result) {
            is TaskUseCaseResult.Success -> {
                Timber.d("Fetched tasks: ${result.tasks}")
                _state.value = TaskListState.Success(tasks = result.tasks)
            }
            is TaskUseCaseResult.Error -> {
                Timber.e("Error fetching tasks: ${result.message}")
                _state.value = TaskListState.Error(message = result.message)
            }
        }
    }

    fun showCalendar(show: Boolean) {
        _showCalendar.value = show
    }
}