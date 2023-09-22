package com.singaludra.weatherapp.domain.usecase

import com.singaludra.weatherapp.domain.Resource
import com.singaludra.weatherapp.domain.base.BaseUseCase
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: IWeatherRepository
): BaseUseCase<String, Flow<Resource<Forecast>>>() {
    override suspend fun execute(parameters: String): Flow<Resource<Forecast>> {
        return repository.getForecastDataWithCityName(parameters)
    }
}