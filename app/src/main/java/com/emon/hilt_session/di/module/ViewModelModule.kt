package com.emon.hilt_session.di.module

import com.emon.hilt_session.CelsiusTemperatureFormatter
import com.emon.hilt_session.FahrenheitTemperatureFormatter
import com.emon.hilt_session.FakeWeatherRepository
import com.emon.hilt_session.RandomTemperatureProvider
import com.emon.hilt_session.TemperatureFormatter
import com.emon.hilt_session.WeatherRepository
import com.emon.hilt_session.di.qualifier.Celsius
import com.emon.hilt_session.di.qualifier.Fahrenheit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @Celsius
    fun provideCelsiusTemperatureFormatter(): TemperatureFormatter {
        return CelsiusTemperatureFormatter()
    }

    @Provides
    @Fahrenheit
    fun provideFahrenheitTemperatureFormatter(): TemperatureFormatter {
        return FahrenheitTemperatureFormatter()
    }

    @Provides
    fun repository(randomTemperatureProvider: RandomTemperatureProvider): WeatherRepository =
        FakeWeatherRepository(randomTemperatureProvider)

}
