package com.emon.hilt_session

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface WeatherRepository {
    fun getTemperatureInCelsius(location: String): Flow<ApiResult<String>>
}

class FakeWeatherRepository(
    private val temperatureProvider: RandomTemperatureProvider
): WeatherRepository {
    override fun getTemperatureInCelsius(location: String) = flow {
        emit(ApiResult.Loading)
        delay(5000)
        emit(ApiResult.Success(temperatureProvider.getRandomNumber().toString()))
    }
}