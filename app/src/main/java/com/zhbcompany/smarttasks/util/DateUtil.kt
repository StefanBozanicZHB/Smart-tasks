package com.zhbcompany.smarttasks.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtil {

    private val serverDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    private val taskItemDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.US)

    /**
     * Converts a LocalDate object to a formatted string using the provided DateTimeFormatter.
     * If no format is provided, the default taskItemDateFormat is used.
     *
     * @param date The LocalDate object to be converted. If null, the function returns null.
     * @param format The DateTimeFormatter used to format the date. The default value is taskItemDateFormat.
     *
     * @return A formatted string representation of the date, or null if the input date is null.
     */
    @TypeConverter
    fun fromDate(date: LocalDate?, format: DateTimeFormatter = taskItemDateFormat): String? {
        return date?.format(format)
    }

    /**
     * Converts a string representation of a date to a LocalDate object using the provided DateTimeFormatter.
     * If the input string is null, the function returns null.
     *
     * @param dateString The string representation of the date to be converted.
     * @param format The DateTimeFormatter used to parse the date string. The default value is serverDateFormat.
     *
     * @return A LocalDate object representing the parsed date, or null if the input dateString is null or an error occurs during parsing.
     */
    @TypeConverter
    fun toDate(dateString: String?, format: DateTimeFormatter = serverDateFormat): LocalDate? {
        return dateString?.let {
            try {
                LocalDate.parse(it, format)
            } catch (e: Exception) {
                null
            }
        }
    }
}