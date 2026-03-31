package com.emon.hilt_session.di.module

import com.emon.hilt_session.FakeLocationProvider
import com.emon.hilt_session.LocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideLocationProvider(): LocationProvider = FakeLocationProvider()
}