package com.emon.hilt_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emon.hilt_session.di.qualifier.Fahrenheit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    @Fahrenheit private val temperatureFormatter: TemperatureFormatter
) : ViewModel() {
    private var _uiState = MutableStateFlow(UIState())
    private var _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    val uiState = _uiState.asStateFlow()

    fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.LoadWeather -> {
                loadWeather(event.location)
            }
        }
    }


    private fun loadWeather(location:String) {
        viewModelScope.launch {
            repository.getTemperatureInCelsius(location).collect {
                when (it) {
                    is ApiResult.Loading -> {
                        _effect.send(Effect.ShowSnackbar("Loading Weather Data"))
                        _uiState.value =
                            _uiState.value.copy(temperature = "", isLoading = true, error = "")
                    }

                    is ApiResult.Success -> {
                        _effect.send(Effect.ShowSnackbar("Successfully loaded Weather Data"))
                        _uiState.value =
                            _uiState.value.copy(temperature = temperatureFormatter.formatTemperature(it.data), isLoading = false, error = "")
                    }

                    is ApiResult.Error -> {
                        _effect.send(Effect.ShowSnackbar("Error loading Weather Data"))
                        _uiState.value =
                            _uiState.value.copy(temperature = "", isLoading = true, error = "Error!")
                    }
                }

            }
        }

    }
}

sealed class WeatherEvent {
    data class LoadWeather(val location:String): WeatherEvent()
}


data class UIState(
    val isLoading: Boolean = false,
    val temperature: String = "",
    val error: String = ""
)

sealed class Effect {
    data class ShowSnackbar(val message: String) : Effect()
}