package com.emon.hilt_session

import javax.inject.Inject
import kotlin.random.Random

class RandomTemperatureProvider @Inject constructor() {
    fun getRandomNumber(): Int {
        return Random.nextInt(from = 12, until = 56)
    }
}