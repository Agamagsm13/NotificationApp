package com.example.testappl

import android.app.Application
import com.example.testappl.di.AppComponent
import com.example.testappl.di.DaggerAppComponent
import timber.log.Timber

class App: Application() {
    companion object {
        var version = ""
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}