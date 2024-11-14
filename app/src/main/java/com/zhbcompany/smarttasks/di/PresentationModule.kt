package com.zhbcompany.smarttasks.di

import com.zhbcompany.smarttasks.domain.use_case.TaskUseCases
import com.zhbcompany.smarttasks.presentation.task_list.TaskListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Creates and provides an instance of [TaskListViewModel].
 *
 * This function is responsible for creating a new instance of [TaskListViewModel] by injecting
 * the required dependencies: [TaskUseCases] and [CoroutineDispatcher].
 *
 * @param uceCase An instance of [TaskUseCases] that provides the necessary business logic for the task list.
 * @param dispatcher A [CoroutineDispatcher] that specifies the context in which the task list view model's
 * coroutines should be executed.
 *
 * @return An instance of [TaskListViewModel] configured with the provided dependencies.
 */
private fun provideTaskListViewModel(
    uceCase: TaskUseCases,
    dispatcher: CoroutineDispatcher
): TaskListViewModel {
    return TaskListViewModel(uceCase, dispatcher)
}

val presentationModule: Module = module {
    viewModel { provideTaskListViewModel(get(), get(named("IoDispatcher"))) }
}