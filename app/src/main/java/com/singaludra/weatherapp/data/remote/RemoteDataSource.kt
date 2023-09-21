package com.singaludra.weatherapp.data.remote

import com.singaludra.weatherapp.data.remote.network.ApiResponse
import com.singaludra.weatherapp.data.remote.network.ApiService
import com.singaludra.weatherapp.data.remote.response.ForecastResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
): IRemoteDataSource {
    override fun getForecastDataWithCityName(cityName: String): Flow<ApiResponse<ForecastResponse>> {
        return flow {
            try {
                val response = apiService.getForecastDataWithCityName(
                    cityName
                )
                if (response.weatherList != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.parse()))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun Exception.parse(): String {
        return when(this){
            is IOException -> {
                "You have no connection!"
            }

            is HttpException -> {
                if(this.code() == 422){
                    "Invalid Data"
                } else {
                    "Invalid request"
                }
            }

            else -> {
                this.message.toString()
            }
        }
    }

}