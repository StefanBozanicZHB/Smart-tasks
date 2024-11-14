package com.zhbcompany.smarttasks.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtil {

    private val serverDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    private val taskItemDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.US)

    @TypeConverter
    fun fromDate(date: LocalDate?, format: DateTimeFormatter = taskItemDateFormat): String? {
        return date?.format(format)
    }

    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return dateString?.let {
            try {
                LocalDate.parse(it, serverDateFormat)
            } catch (e: Exception) {
                null
            }
        }
    }
}