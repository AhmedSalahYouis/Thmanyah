package com.thamaneya.androidchallenge.core.domain

import com.thamaneya.androidchallenge.core.domain.search.SearchUseCase
import com.thamaneya.androidchallenge.core.domain.search.SearchUseCaseImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    // Use case implementations
    single<SearchUseCase> { 
        SearchUseCaseImpl(get(), Dispatchers.IO)
    }
}
