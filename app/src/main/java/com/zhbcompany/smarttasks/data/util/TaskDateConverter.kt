package com.zhbcompany.smarttasks.data.util

import androidx.room.TypeConverter
import java.time.LocalDate

/**
 * A utility class for converting between [LocalDate] and its string representation.
 * This class is used by Room to support type conversions for date fields in the database.
 */
class TaskDateConverter {

    /**
     * Converts a [LocalDate] object to a string representation.
     *
     * @param date The [LocalDate] object to be converted. If null, the function returns null.
     * @return A string representation of the input [LocalDate] object, or null if the input was null.
     */
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }

    /**
     * Converts a string representation of a date to a [LocalDate] object.
     *
     * @param dateString The string representation of a date to be converted. If null, the function returns null.
     * @return A [LocalDate] object representing the input string, or null if the input was null.
     * @throws IllegalArgumentException If the input string cannot be parsed into a valid [LocalDate].
     */
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
}
