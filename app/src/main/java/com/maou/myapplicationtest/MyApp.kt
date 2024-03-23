package com.maou.myapplicationtest

import android.app.Application
import com.maou.myapplicationtest.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            androidLogger(Level.ERROR)
            loadKoinModules(
                dataModule
            )
        }
    }
}