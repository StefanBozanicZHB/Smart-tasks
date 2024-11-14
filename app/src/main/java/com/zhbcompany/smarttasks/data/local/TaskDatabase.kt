package com.zhbcompany.smarttasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zhbcompany.smarttasks.data.local.dto.TaskLocal
import com.zhbcompany.smarttasks.data.util.TaskDateConverter
import com.zhbcompany.smarttasks.data.util.TaskStatusConverter

/**
 * A Room database for storing and retrieving task data locally.
 *
 * This class is annotated with `@Database` to specify the entities that will be included in the database,
 * the version of the database schema, and whether the schema should be exported.
 *
 * The `@TypeConverters` annotation is used to specify custom type converters for date and status fields.
 *
 * @see TaskLocal for the entity class representing a task.
 * @see TaskDateConverter for the custom type converter for date fields.
 * @see TaskStatusConverter for the custom type converter for status fields.
 */
@Database(
    entities = [TaskLocal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TaskDateConverter::class, TaskStatusConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    /**
     * An abstract property representing the data access object (DAO) for interacting with the task data.
     */
    abstract val dao: TaskDao

    /**
     * A companion object containing a constant representing the name of the database file.
     */
    companion object {
        const val DATABASE_NAME = "smart_tasks_database"
    }
}
