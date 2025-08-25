package com.thamaneya.androidchallenge.feature.search

import com.thamaneya.androidchallenge.core.domain.domainModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    includes(domainModule)
    viewModel { SearchViewModel(get()) }
}

