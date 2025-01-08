package com.stc.openweatherapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree()) // Use a custom tree for release builds.
        }
    }
}

// Optional: A custom tree for release builds
class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            // Log only errors and warnings in production
        }
    }
}