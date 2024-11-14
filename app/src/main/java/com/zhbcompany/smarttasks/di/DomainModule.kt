package com.zhbcompany.smarttasks.di

import com.zhbcompany.smarttasks.domain.repo.TaskRepository
import com.zhbcompany.smarttasks.domain.use_case.TaskUseCases
import org.koin.core.module.Module
import org.koin.dsl.module

private fun provideTaskUseCases(repo: TaskRepository): TaskUseCases {
    return TaskUseCases(repo)
}

val domainModule: Module = module {
    single { provideTaskUseCases(get()) }
}