package com.zhbcompany.smarttasks.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule: Module = module {
    single<CoroutineDispatcher>(named("IoDispatcher")) { Dispatchers.IO }
}