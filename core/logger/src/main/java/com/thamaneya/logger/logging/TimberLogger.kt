package com.thamaneya.logger.logging

import timber.log.Timber

/**
 * Implementation of ITimberLogger using Timber library
 */
class TimberLogger : ITimberLogger {

    override fun logError(message: String, exception: Exception?) {
        Timber.e(exception, message)
    }

    override fun logWarning(message: String) {
        Timber.w(message)
    }

    override fun logInfo(message: String) {
        Timber.i(message)
    }

    override fun logDebug(message: String) {
        Timber.d(message)
    }

    override fun logVerbose(message: String) {
        Timber.v(message)
    }
}
