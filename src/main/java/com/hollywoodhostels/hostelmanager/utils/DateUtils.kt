package com.hollywoodhostels.hostelmanager.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatDate(date: Date, pattern: String = "dd/MM/yyyy"): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }

    fun getCurrentDate(): Date {
        return Calendar.getInstance().time
    }

    fun getMonthName(month: Int): String {
        return when (month) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> "Unknown"
        }
    }
}