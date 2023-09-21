package com.singaludra.weatherapp.data

import com.singaludra.weatherapp.data.local.entity.mapToDomain
import com.singaludra.weatherapp.data.local.room.dao.CityDao
import com.singaludra.weatherapp.data.remote.IRemoteDataSource
import com.singaludra.weatherapp.data.remote.network.ApiResponse
import com.singaludra.weatherapp.data.remote.response.ForecastResponse
import com.singaludra.weatherapp.data.remote.response.mapToDomain
import com.singaludra.weatherapp.domain.Resource
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.model.mapToEntity
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val cityDao: CityDao,
    private val remoteDataSource: IRemoteDataSource
): IWeatherRepository {
    override fun getForecastDataWithCityName(cityName: String): Flow<Resource<Forecast>> {
        return object : RepositoryLoader<ForecastResponse, Forecast>(){
            override suspend fun createCall(): Flow<ApiResponse<ForecastResponse>> {
                return remoteDataSource.getForecastDataWithCityName(cityName)
            }

            override fun mapApiResponseToDomain(data: ForecastResponse): Forecast {
                return data.mapToDomain()
            }

        }.asFlow()
    }

    override fun getMyCity(): Flow<List<Forecast.City>> {
        return cityDao.getMyCity().map {
            it.map { city ->
                city.mapToDomain()
            }
        }
    }

    override suspend fun addCity(city: Forecast.City) {
        cityDao.addCity(city.mapToEntity())
    }

}