package com.singaludra.weatherapp.domain.usecase

import com.singaludra.weatherapp.domain.base.BaseUseCase
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyCityUseCase @Inject constructor(
    private val repository: IWeatherRepository
): BaseUseCase<Unit, Flow<List<Forecast.City>>>() {
    override suspend fun execute(parameters: Unit): Flow<List<Forecast.City>> {
        return repository.getMyCity()
    }
}