package com.thamaneya.androidchallenge.core.ui.formatter

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.thamaneya.androidchallenge.core.ui.R
import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {
    @Composable
    fun formatDate(date: String?): String {
        if (date.isNullOrEmpty()) return ""

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val parsedDate = try {
            inputFormat.parse(date)
        } catch (e: Exception) {
            null
        }
        if (parsedDate == null) return ""

        val now = System.currentTimeMillis() / 1000
        val diff = now - (parsedDate.time / 1000)

        return when {
            diff < 60 -> stringResource(R.string.duration_now)
            diff < 3600 -> {
                val minutes = diff / 60
                stringResource(if (minutes == 1L) R.string.duration_one_minute_ago else R.string.duration_minutes_ago, minutes)
            }
            diff < 86400 -> {
                val hours = diff / 3600
                stringResource(if (hours == 1L) R.string.duration_one_hour_ago else R.string.duration_hours_ago, hours)
            }
            diff < 604800 -> {
                val days = diff / 86400
                stringResource(if (days == 1L) R.string.duration_one_day_ago else R.string.duration_days_ago, days)
            }
            diff < 2592000 -> { // 30 days
                val weeks = diff / 604800
                stringResource(if (weeks == 1L) R.string.duration_one_week_ago else R.string.duration_weeks_ago, weeks)
            }
            diff < 31536000 -> { // 365 days
                val months = diff / 2592000
                stringResource(if (months == 1L) R.string.duration_one_month_ago else R.string.duration_months_ago, months)
            }
            else -> {
                val years = diff / 31536000
                stringResource(if (years == 1L) R.string.duration_one_year_ago else R.string.duration_years_ago, years)
            }
        }
    }

}