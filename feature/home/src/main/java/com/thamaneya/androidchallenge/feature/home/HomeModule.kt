package com.thamaneya.androidchallenge.feature.home

import com.thamaneya.logger.logging.ITimberLogger
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { 
        HomeViewModel(
            repository = get(),
            logger = get<ITimberLogger>()
        )
    }
}




