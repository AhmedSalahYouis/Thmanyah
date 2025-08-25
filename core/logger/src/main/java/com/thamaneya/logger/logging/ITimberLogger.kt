package com.thamaneya.logger.logging

/**
 * Interface for logging functionality using Timber
 */
interface ITimberLogger {
    /**
     * Log an error message with optional exception
     * @param message The error message to log
     * @param exception Optional exception to log with the message
     */
    fun logError(message: String, exception: Exception? = null)
    
    /**
     * Log a warning message
     * @param message The warning message to log
     */
    fun logWarning(message: String)
    
    /**
     * Log an info message
     * @param message The info message to log
     */
    fun logInfo(message: String)
    
    /**
     * Log a debug message
     * @param message The debug message to log
     */
    fun logDebug(message: String)
    
    /**
     * Log a verbose message
     * @param message The verbose message to log
     */
    fun logVerbose(message: String)
}
