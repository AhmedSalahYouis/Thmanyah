package com.thamaneya.logger.logging

import org.koin.dsl.module

/**
 * Koin module for logging dependencies
 */
val loggingModule = module {
    single<ITimberLogger> { TimberLogger() }
}
