package com.thamaneya.androidchallenge

import android.app.Application
import com.thamaneya.androidchallenge.core.data.dataModule
import com.thamaneya.androidchallenge.core.network.networkModule
import com.thamaneya.androidchallenge.feature.home.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ThmanyahApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ThmanyahApplication)
            modules(
                networkModule,
                dataModule,
                homeModule,

                )
        }
    }
}
