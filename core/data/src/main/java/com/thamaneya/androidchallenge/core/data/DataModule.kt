package com.thamaneya.androidchallenge.core.data

import com.thamaneya.androidchallenge.core.data.home.HomeRemoteMediator
import com.thamaneya.androidchallenge.core.data.home.HomeRepository
import com.thamaneya.androidchallenge.core.data.home.HomeRepositoryImpl
import com.thamaneya.androidchallenge.core.data.home.local.AppDatabase
import com.thamaneya.androidchallenge.core.data.mapper.HomeEntityMapper
import com.thamaneya.androidchallenge.core.data.mapper.HomeSectionMapper
import com.thamaneya.androidchallenge.core.data.search.SearchRepositoryImpl
import com.thamaneya.androidchallenge.core.domain.search.SearchRepository
import com.thamaneya.logger.logging.ITimberLogger
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    // Database
    single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
    
    // Mappers
    single<HomeSectionMapper> { HomeSectionMapper(logger = get<ITimberLogger>()) }
    single<HomeEntityMapper> { HomeEntityMapper() }
    
    // RemoteMediator
    single<HomeRemoteMediator> {
        HomeRemoteMediator(
            api = get(),
            database = get(),
            mapper = get(),
            logger = get<ITimberLogger>(),
            dataErrorProvider = get()
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
    
    // Search Repository
    single<SearchRepository> {
        SearchRepositoryImpl(
            api = get(),
            mapper = get(),
            dataErrorProvider = get(),
            logger = get()
        )
    }
    

}
