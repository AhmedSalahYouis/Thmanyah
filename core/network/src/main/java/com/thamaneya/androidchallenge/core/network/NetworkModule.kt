package com.thamaneya.androidchallenge.core.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val HOME_RETROFIT = "homeRetrofit"
private const val SEARCH_RETROFIT = "searchRetrofit"

val networkModule = module {

    single<NetworkMonitor> { ConnectivityManagerNetworkMonitor(get()) }

    single {
        GsonBuilder()
            .create()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // HomeApi Retrofit instance
    single(named(HOME_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl("https://api-v2-b2sit6oh3a-uc.a.run.app")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    // SearchApi Retrofit instance
    single(named(SEARCH_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl("https://mock.apidog.com/m1/735111-711675-default/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    // HomeApi service
    single {
        get<Retrofit>(named(HOME_RETROFIT)).create(HomeApi::class.java)
    }

    // SearchApi service
    single {
        get<Retrofit>(named(SEARCH_RETROFIT)).create(SearchApi::class.java)
    }
}
