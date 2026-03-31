package com.emon.hilt_session

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class WeatherSnackbarManager(
    private val activity: ComponentActivity
) {
    fun showSnackbar(
        snackbarHostState: SnackbarHostState,
        message: String
    ) {
        activity.lifecycleScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}