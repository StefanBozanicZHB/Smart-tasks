package com.zhbcompany.smarttasks.di

import android.content.Context
import androidx.room.Room
import com.zhbcompany.smarttasks.BuildConfig
import com.zhbcompany.smarttasks.data.cache.TaskCache
import com.zhbcompany.smarttasks.data.local.TaskDao
import com.zhbcompany.smarttasks.data.local.TaskDatabase
import com.zhbcompany.smarttasks.data.remote.TaskApiService
import com.zhbcompany.smarttasks.data.repo.TaskRepositoryImpl
import com.zhbcompany.smarttasks.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Creates and configures a Room database for storing task data.
 *
 * @param context The application context.
 * @return A configured instance of [TaskDatabase].
 *
 * This function uses the Room database builder to create a new database instance.
 * The database name is defined by [TaskDatabase.DATABASE_NAME].
 * If the database already exists on disk and the schema doesn't match the one defined in the code,
 * Room will destroy the existing database and recreate it.
 * This behavior is controlled by [RoomDatabase.Builder.fallbackToDestructiveMigration].
 */
private fun provideDatabase(context: Context): TaskDatabase {
    return Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        TaskDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

private fun provideTaskDao(database: TaskDatabase): TaskDao {
    return database.dao
}

/**
 * Creates and configures a Retrofit instance for making network requests.
 *
 * @return A configured instance of [Retrofit].
 *
 * This function uses Retrofit's builder to create a new instance.
 * The base URL is obtained from [BuildConfig.BASE_URL].
 * The Gson converter factory is added to handle JSON responses.
 *
 * Note: The Retrofit instance is responsible for making network requests to the specified base URL.
 * It uses the provided converter factory to serialize and deserialize data to and from JSON.
 */
private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideRetrofitApi(retrofit: Retrofit): TaskApiService {
    return retrofit.create(TaskApiService::class.java)
}

/**
 * Creates and configures a [TaskRepository] instance for managing task data.
 *
 * @param db The instance of [TaskDatabase] for local data storage.
 * @param api The instance of [TaskApiService] for making network requests.
 * @param taskCache The instance of [TaskCache] for caching task data.
 * @param dispatcher The [CoroutineDispatcher] for executing tasks in the IO context.
 *
 * @return A configured instance of [TaskRepository].
 *
 * This function creates a new instance of [TaskRepositoryImpl] by passing the provided parameters.
 * The [TaskRepositoryImpl] class is responsible for handling data operations, such as fetching tasks,
 * updating tasks. It uses the provided database, API service, cache, and dispatcher
 * to perform these operations efficiently.
 */
private fun provideTodoRepo(
    db: TaskDatabase,
    api: TaskApiService,
    taskCache: TaskCache,
    dispatcher: CoroutineDispatcher
): TaskRepository {
    return TaskRepositoryImpl(db.dao, api, taskCache, dispatcher)
}

val dataModule: Module = module {
    single { provideDatabase(androidContext()) }
    single { provideTaskDao(get()) }
    single { provideRetrofit() }
    single { provideRetrofitApi(get()) }
    single { TaskCache() }
    single { provideTodoRepo(get(), get(), get(), get(named("IoDispatcher"))) }
}
