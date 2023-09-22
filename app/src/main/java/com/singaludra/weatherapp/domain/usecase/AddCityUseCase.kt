package com.singaludra.weatherapp.domain.usecase

import com.singaludra.weatherapp.domain.base.BaseUseCase
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import javax.inject.Inject

class AddCityUseCase @Inject constructor(
    private val repository: IWeatherRepository
): BaseUseCase<Forecast.City, Unit>() {
    override suspend fun execute(parameters: Forecast.City) {
        repository.addCity(parameters)
    }
}