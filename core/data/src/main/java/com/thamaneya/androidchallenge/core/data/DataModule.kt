package com.thamaneya.androidchallenge.core.data

import com.thamaneya.androidchallenge.core.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    // Database
    single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
    
    // Mappers
    single<HomeSectionMapper> { HomeSectionMapper() }
    single<HomeEntityMapper> { HomeEntityMapper() }
    
    // RemoteMediator
    single<HomeRemoteMediator> { 
        HomeRemoteMediator(
            api = get(),
            database = get(),
            mapper = get()
        )
    }
    
    // Repository
    single<HomeRepository> { 
        HomeRepositoryImpl(
            remoteMediator = get(),
            database = get(),
            entityMapper = get()
        )
    }
}
