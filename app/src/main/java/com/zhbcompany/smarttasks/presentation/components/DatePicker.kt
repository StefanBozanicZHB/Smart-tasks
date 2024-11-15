package com.zhbcompany.smarttasks.presentation.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import com.zhbcompany.smarttasks.R
import java.time.LocalDate
import java.util.Calendar

@Composable
fun ShowDatePicker(context: Context, currentDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance().apply {
        set(currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth)
    }

    DatePickerDialog(
        context,
        R.style.DatePickerTheme,
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    ).show()
}