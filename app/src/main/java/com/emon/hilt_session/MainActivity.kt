package com.emon.hilt_session

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.emon.hilt_session.ui.theme.HiltsessionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val snackbarManager = WeatherSnackbarManager(this)
    private val locationProvider: LocationProvider = FakeLocationProvider()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HiltsessionTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    WeatherScreen(
                        snackbarManager,
                        snackbarHostState,
                        locationProvider,
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(
    snackbarManager: WeatherSnackbarManager,
    snackbarHostState: SnackbarHostState,
    locationProvider: LocationProvider,
    modifier: Modifier = Modifier
) {
    val randomTemperatureProvider = RandomTemperatureProvider()
    val repository = FakeWeatherRepository(randomTemperatureProvider)
    val temperatureFormatter = CelsiusTemperatureFormatter()
    val viewModel = remember { WeatherViewModel(repository, temperatureFormatter) }

    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(WeatherEvent.LoadWeather(locationProvider.provideLocation()))
        viewModel.effect.collect {
            when (it) {
                is Effect.ShowSnackbar -> snackbarManager.showSnackbar(
                    snackbarHostState,
                    it.message
                )
            }
        }
    }


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.value.isLoading) {
            CircularProgressIndicator()
        } else if (state.value.error.isNotEmpty()) {
            Text(
                text = state.value.error,
                style = MaterialTheme.typography.displayMedium.copy(color = Color.Red)
            )
        } else {
            Text(
                text = state.value.temperature,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = locationProvider.provideLocation(),
                style = MaterialTheme.typography.bodyLarge
            )
        }


    }

}