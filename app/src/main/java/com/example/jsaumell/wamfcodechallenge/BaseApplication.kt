package com.example.jsaumell.wamfcodechallenge

import android.app.Application
import com.example.jsaumell.wamfcodechallenge.di.appModule
import org.koin.android.ext.android.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}
