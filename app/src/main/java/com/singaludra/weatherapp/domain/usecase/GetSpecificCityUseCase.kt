package com.singaludra.weatherapp.domain.usecase

import com.singaludra.weatherapp.domain.base.BaseUseCase
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetSpecificCityUseCase @Inject constructor(
    private val repository: IWeatherRepository
): BaseUseCase<String, Boolean>() {
    override suspend fun execute(parameters: String): Boolean {
        return repository.getSpecificCity(parameters)
    }
}