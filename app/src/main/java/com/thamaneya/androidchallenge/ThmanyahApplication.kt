package com.thamaneya.androidchallenge

import android.app.Application
import com.thamaneya.androidchallenge.core.data.dataModule
import com.thamaneya.androidchallenge.core.data.dispatcherModule
import com.thamaneya.androidchallenge.core.network.networkModule
import com.thamaneya.androidchallenge.feature.home.homeModule
import com.thamaneya.androidchallenge.feature.search.searchModule
import com.thamaneya.error.errorModule
import com.thamaneya.logger.logging.loggingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class ThmanyahApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ThmanyahApplication)
            modules(
                networkModule,
                dataModule,
                dispatcherModule,
                homeModule,
                searchModule,
                errorModule,
                loggingModule
            )
        }
    }
}
