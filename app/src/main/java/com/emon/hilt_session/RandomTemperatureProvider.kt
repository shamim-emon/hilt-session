package com.emon.hilt_session

import kotlin.random.Random

class RandomTemperatureProvider {
    fun getRandomNumber(): Int {
        return Random.nextInt(from = 12, until = 56)
    }
}