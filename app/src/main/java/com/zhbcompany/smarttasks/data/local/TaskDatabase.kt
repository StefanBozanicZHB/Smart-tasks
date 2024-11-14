package com.zhbcompany.smarttasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zhbcompany.smarttasks.data.local.dto.TaskLocal
import com.zhbcompany.smarttasks.data.util.TaskDateConverter
import com.zhbcompany.smarttasks.data.util.TaskStatusConverter

@Database(
    entities = [TaskLocal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TaskDateConverter::class, TaskStatusConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract val dao: TaskDao

    companion object {
        const val DATABASE_NAME = "smart_tasks_database"
    }
}
