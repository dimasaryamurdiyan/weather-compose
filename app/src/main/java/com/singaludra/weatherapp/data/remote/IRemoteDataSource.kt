package com.singaludra.weatherapp.data.remote

import com.singaludra.weatherapp.data.remote.network.ApiResponse
import com.singaludra.weatherapp.data.remote.response.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    fun getForecastDataWithCityName(cityName: String): Flow<ApiResponse<ForecastResponse>>
}