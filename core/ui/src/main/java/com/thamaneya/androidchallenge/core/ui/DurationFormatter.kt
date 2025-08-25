package com.thamaneya.androidchallenge.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.thamaneya.androidchallenge.core.ui.R

/**
 * Utility class for formatting duration in seconds to human-readable format
 */
object DurationFormatter {
    
    /**
     * Format duration in seconds to localized format
     * - Less than 1 hour: "30 min" or "30 د"
     * - 1 hour or more: "1:30 h" or "1:30 س"
     * @param seconds Duration in seconds, can be null
     * @return Formatted string like "30 min", "1:30 h", or empty string for invalid input
     */
    @Composable
    fun formatDuration(seconds: Int?): String {
        if (seconds == null || seconds <= 0) return ""
        
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        
        return when {
            hours > 0 -> {
                val hoursText = stringResource(R.string.duration_hours)
                if (minutes > 0) {
                    "$hours:$minutes $hoursText"
                } else {
                    "$hours $hoursText"
                }
            }
            minutes > 0 -> {
                val minutesText = stringResource(R.string.duration_minutes)
                "$minutes $minutesText"
            }
            else -> stringResource(R.string.duration_less_than_minute)
        }
    }
}
