package com.thamaneya.error

import org.koin.dsl.module

val errorModule = module {
    single<IDataErrorProvider> { DataErrorProvider() }
}
