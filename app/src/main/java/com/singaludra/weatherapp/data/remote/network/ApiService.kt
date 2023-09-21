package com.singaludra.weatherapp.data.remote.network

import com.singaludra.weatherapp.data.remote.response.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/data/2.5/forecast")
    suspend fun getForecastDataWithCityName(
        @Query("q") cityName: String,
        @Query("APPID") apiKey: String ="",
        @Query("units") units: String = "metcric",
    ): ForecastResponse
}