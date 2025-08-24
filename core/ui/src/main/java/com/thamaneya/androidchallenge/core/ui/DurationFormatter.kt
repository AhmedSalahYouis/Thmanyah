package com.thamaneya.androidchallenge.core.ui

import java.util.Locale

/**
 * Utility class for formatting duration in seconds to human-readable format
 */
object DurationFormatter {
    
    /**
     * Format duration in seconds to mm:ss or hh:mm:ss format
     */
    fun formatDuration(seconds: Int?): String {
        if (seconds == null || seconds <= 0) return ""
        
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60
        
        return if (hours > 0) {
            String.format(Locale.US, "%d:%02d:%02d", hours, minutes, remainingSeconds)
        } else {
            String.format(Locale.US, "%d:%02d", minutes, remainingSeconds)
        }
    }
    
}

