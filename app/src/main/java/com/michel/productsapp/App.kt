package com.michel.productsapp

import android.app.Application
import android.util.Log
import com.michel.productsapp.di.appModule
import com.michel.productsapp.di.dataModule
import com.michel.productsapp.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Log.i("APP", "APPLICATION STARTED")

        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }

}