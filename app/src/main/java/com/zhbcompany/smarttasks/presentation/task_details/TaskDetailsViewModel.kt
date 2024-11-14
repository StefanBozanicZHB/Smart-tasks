package com.zhbcompany.smarttasks.presentation.task_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhbcompany.smarttasks.domain.model.TaskDomain
import com.zhbcompany.smarttasks.domain.model.TaskStatus
import com.zhbcompany.smarttasks.domain.use_case.TaskUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class TaskDetailsViewModel(
    private val taskUseCases: TaskUseCases,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    // State management
    private val _state = MutableStateFlow<TaskUpdateState>(TaskUpdateState.Loading)
    val state = _state.asStateFlow()

    private val _showCommentQuestionDialog = MutableStateFlow(false)
    val showCommentQuestionDialog= _showCommentQuestionDialog.asStateFlow()

    private val _showCommentInputDialog = MutableStateFlow(false)
    val showCommentInputDialog= _showCommentInputDialog.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _commentInputText = MutableStateFlow("")
    val commentInputText: StateFlow<String> = _commentInputText

    private var currentTask: TaskDomain? = null
    
    /**
     * Loads a task from the data source based on the given ID and updates the UI state accordingly.
     *
     * @param id The unique identifier of the task to be loaded.
     *
     * This function initiates a coroutine to fetch the task from the data source using the provided
     * [taskUseCases] and [dispatcher]. It updates the UI state to [TaskUpdateState.Loading] before
     * attempting to load the task. If the task is successfully found, the UI state is updated to
     * [TaskUpdateState.Success] with the loaded task. If the task is not found, an error message is logged
     * using Timber, and the UI state remains unchanged.
     */
    fun loadTask(id: String) {
        Timber.d("Loading task | ID: $id")
        viewModelScope.launch(dispatcher) {
            _state.value = TaskUpdateState.Loading
            currentTask = (taskUseCases.getTaskById(id)?.also { task ->
                _state.value = TaskUpdateState.Success(task)
            } ?: run {
                Timber.e("Task not found | ID: $id")
            }) as TaskDomain?
        }
    }

    fun resolveTask() = updateTaskStatus(TaskStatus.RESOLVED)

    fun cantResolveTask() = updateTaskStatus(TaskStatus.CANT_RESOLVE)

    private fun updateTaskStatus(status: TaskStatus) {
        viewModelScope.launch(dispatcher) {
            currentTask = currentTask?.copy(status = status)
            saveCurrentTask()
            _eventFlow.emit(UiEvent.SaveStatusTask)
        }
    }

    /**
     * Saves the current task to the data source.
     *
     * This function is responsible for updating the current task in the data source. It retrieves the current task
     * from the [currentTask] property and attempts to save it using the [taskUseCases.saveTask] function. If an
     * exception occurs during the save operation, an error message is logged using Timber.
     *
     * @throws Exception If an error occurs while saving the task.
     *
     * @return Unit
     */
    private suspend fun saveCurrentTask() {
        currentTask?.let { task ->
            try {
                taskUseCases.saveTask(task)
            } catch (e: Exception) {
                Timber.e("Failed to save task | ${e.message}")
            }
        }
    }

    fun onBack() = emitEvent(UiEvent.Back)

    fun showCommentQuestionDialog() = toggleCommentDialogs(showQuestion = true)

    fun showCommentInputDialog() = toggleCommentDialogs(showInput = true)

    private fun toggleCommentDialogs(showQuestion: Boolean = false, showInput: Boolean = false) {
        viewModelScope.launch(dispatcher) {
            _showCommentQuestionDialog.emit(showQuestion)
            _showCommentInputDialog.emit(showInput)
        }
    }

    fun onQuestionDialogResponse(response: Boolean) {
        viewModelScope.launch(dispatcher) {
            _showCommentQuestionDialog.emit(false)
            _eventFlow.emit(UiEvent.ResponseCommentQuestion(response))
        }
    }

    fun onInputTextChange(newText: String) {
        _commentInputText.value = newText
    }

    /**
     * Handles the response from the comment input dialog.
     *
     * This function is responsible for processing the user's response to the comment input dialog.
     * It retrieves the current comment text from the `_commentInputText` state flow, resets the comment input,
     * and then updates the UI state and triggers specific events based on the user's response.
     *
     * @param save A boolean indicating whether the user chose to save the comment.
     *
     * @return Unit
     */
    fun onInputDialogResponse(save: Boolean) {
        val comment = _commentInputText.value
        resetCommentInput()
        viewModelScope.launch(dispatcher) {
            _showCommentInputDialog.emit(false)
            if (save) {
                currentTask = currentTask?.copy(comment = comment)
                saveCurrentTask()
                _eventFlow.emit(UiEvent.SaveCommentTask)
            } else {
                _eventFlow.emit(UiEvent.CancelInputCommentTask)
            }
        }
    }

    private fun resetCommentInput() {
        _commentInputText.value = ""
    }

    private fun emitEvent(event: UiEvent) {
        viewModelScope.launch(dispatcher) {
            _eventFlow.emit(event)
        }
    }

    /**
     * This sealed class represents different types of events that can occur in the TaskDetailsViewModel.
     * These events are used to communicate with the UI layer and trigger specific actions.
     */
    sealed class UiEvent {
        /**
         * Event triggered when the user requests to go back to the previous screen.
         */
        object Back : UiEvent()

        /**
         * Event triggered when the user confirms saving the current task status.
         */
        object SaveStatusTask : UiEvent()

        /**
         * Event triggered when the user responds to the comment question dialog.
         *
         * @param answer The user's answer to the comment question.
         */
        data class ResponseCommentQuestion(val answer: Boolean) : UiEvent()

        /**
         * Event triggered when the user confirms saving the current task comment.
         */
        object SaveCommentTask : UiEvent()

        /**
         * Event triggered when the user cancels inputting a comment.
         */
        object CancelInputCommentTask : UiEvent()
    }
}