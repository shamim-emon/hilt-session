package com.emon.hilt_session

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}