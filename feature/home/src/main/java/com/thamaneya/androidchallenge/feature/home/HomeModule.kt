package com.thamaneya.androidchallenge.feature.home

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { 
        HomeViewModel(
            repository = get(),
            database = get(),
            entityMapper = get(),
        )
    }
}




