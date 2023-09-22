package com.singaludra.weatherapp.di

import com.singaludra.weatherapp.data.WeatherRepository
import com.singaludra.weatherapp.data.remote.IRemoteDataSource
import com.singaludra.weatherapp.data.remote.RemoteDataSource
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(weatherRepository: WeatherRepository): IWeatherRepository

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource
}