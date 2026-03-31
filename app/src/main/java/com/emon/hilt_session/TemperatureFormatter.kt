package com.emon.hilt_session

interface TemperatureFormatter {
    fun formatTemperature(temperature: String): String
}

class CelsiusTemperatureFormatter : TemperatureFormatter {
    override fun formatTemperature(temperature: String): String {
        return "$temperature°C"
    }
}

class FahrenheitTemperatureFormatter : TemperatureFormatter {
    override fun formatTemperature(temperature: String): String {
        val temp = (temperature.toDouble() * 9 / 5) + 32
        return "$temp°F"
    }
}
