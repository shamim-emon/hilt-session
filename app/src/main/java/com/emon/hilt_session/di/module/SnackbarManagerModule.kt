package com.emon.hilt_session.di.module

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import com.emon.hilt_session.WeatherSnackbarManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class SnackbarManagerModule {

    @Provides
    fun provideSnackbarManager(activity: Activity): WeatherSnackbarManager {
        return WeatherSnackbarManager(activity as ComponentActivity)
    }

    @Provides
    fun provideSnackbarHostState(): SnackbarHostState {
        return SnackbarHostState()
    }
}