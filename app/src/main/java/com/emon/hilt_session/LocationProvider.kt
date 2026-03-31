package com.emon.hilt_session

interface LocationProvider {
    fun provideLocation():String
}

class FakeLocationProvider: LocationProvider {
    override fun provideLocation() = "Chan Mia Housing, Mohammadpur, Dhaka-1207"
}