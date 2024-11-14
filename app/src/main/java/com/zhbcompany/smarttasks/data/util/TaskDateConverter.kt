package com.zhbcompany.smarttasks.data.util

import androidx.room.TypeConverter
import java.time.LocalDate

class TaskDateConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
}
