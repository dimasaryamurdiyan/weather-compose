package com.singaludra.weatherapp.domain.repository

import com.singaludra.weatherapp.domain.Resource
import com.singaludra.weatherapp.domain.model.Forecast
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    fun getForecastDataWithCityName(cityName: String): Flow<Resource<Forecast>>
    fun getMyCity(): Flow<List<Forecast.City>>
    suspend fun addCity(city: Forecast.City)
    suspend fun getSpecificCity(cityName: String): Boolean
}