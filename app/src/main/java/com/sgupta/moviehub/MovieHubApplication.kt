package com.sgupta.moviehub

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieHubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}